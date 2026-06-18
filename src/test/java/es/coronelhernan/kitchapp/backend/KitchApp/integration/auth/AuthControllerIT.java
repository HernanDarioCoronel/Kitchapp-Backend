package es.coronelhernan.kitchapp.backend.KitchApp.integration.auth;

import es.coronelhernan.kitchapp.backend.KitchApp.integration.BaseIntegrationTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests de integración para POST /api/auth/login, /refresh y /logout.
 *
 * Crea un usuario de prueba con contraseña conocida en @BeforeAll usando el
 * PasswordEncoder real, evitando depender del hash del seed de producción.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerIT extends BaseIntegrationTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private static final UUID TEST_EMPLOYEE_ID = UUID.fromString("aaaaaaaa-1111-1111-1111-aaaaaaaaaaaa");
    private static final String TEST_USERNAME = "integtest@kitchapp.test";
    private static final String TEST_PASSWORD = "IntegTest123!";

    // Almacena el refreshToken obtenido en el test de login para reutilizarlo
    private String capturedRefreshToken;

    @BeforeAll
    void createTestAuthUser() {
        String hash = passwordEncoder.encode(TEST_PASSWORD);
        // TransactionTemplate garantiza commit explícito: con JPA, Hikari opera en
        // auto-commit=false y cada jdbc.update() suelto quedaría sin commitear.
        new TransactionTemplate(transactionManager).execute(status -> {
            jdbc.update(
                    "INSERT INTO employees (id, full_name, role, is_active, created_at) " +
                    "VALUES (?, 'Integration Test User', 'ADMIN', true, NOW()) " +
                    "ON CONFLICT (id) DO NOTHING",
                    TEST_EMPLOYEE_ID
            );
            jdbc.update(
                    "INSERT INTO auth_users (id, employee_id, username, password_hash, is_active) " +
                    "VALUES (gen_random_uuid(), ?, ?, ?, true) " +
                    "ON CONFLICT (username) DO NOTHING",
                    TEST_EMPLOYEE_ID, TEST_USERNAME, hash
            );
            return null;
        });
    }

    @AfterAll
    void deleteTestAuthUser() {
        new TransactionTemplate(transactionManager).execute(status -> {
            jdbc.update("DELETE FROM refresh_tokens WHERE auth_user_id = " +
                        "(SELECT id FROM auth_users WHERE username = ?)", TEST_USERNAME);
            jdbc.update("DELETE FROM auth_users WHERE username = ?", TEST_USERNAME);
            jdbc.update("DELETE FROM employees WHERE id = ?", TEST_EMPLOYEE_ID);
            return null;
        });
    }

    // ─── Login ───────────────────────────────────────────────────────────────

    @Test
    @Order(1)
    @DisplayName("POST /api/auth/login → 200 con credenciales válidas")
    void login_validCredentials_returns200WithTokens() throws Exception {
        String body = """
                {"username": "%s", "password": "%s"}
                """.formatted(TEST_USERNAME, TEST_PASSWORD);

        String response = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty())
                .andExpect(jsonPath("$.expiresIn").isNumber())
                .andReturn()
                .getResponse()
                .getContentAsString();

        capturedRefreshToken = objectMapper.readTree(response).get("refreshToken").asText();
    }

    @Test
    @Order(2)
    @DisplayName("POST /api/auth/login → 401 con contraseña incorrecta")
    void login_wrongPassword_returns401() throws Exception {
        String body = """
                {"username": "%s", "password": "WrongPass999!"}
                """.formatted(TEST_USERNAME);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(3)
    @DisplayName("POST /api/auth/login → 401 con usuario inexistente")
    void login_unknownUser_returns401() throws Exception {
        String body = """
                {"username": "nobody@test.local", "password": "AnyPass123!"}
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(4)
    @DisplayName("POST /api/auth/login → 400 cuando faltan campos requeridos")
    void login_missingFields_returns400() throws Exception {
        String body = """
                {"username": ""}
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    // ─── Refresh ─────────────────────────────────────────────────────────────

    @Test
    @Order(5)
    @DisplayName("POST /api/auth/refresh → 200 con refreshToken válido")
    void refresh_validToken_returns200WithNewTokens() throws Exception {
        Assumptions.assumeTrue(capturedRefreshToken != null,
                "Se requiere refreshToken del test de login");

        String body = """
                {"refreshToken": "%s"}
                """.formatted(capturedRefreshToken);

        mockMvc.perform(post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty());
    }

    @Test
    @Order(6)
    @DisplayName("POST /api/auth/refresh → 4xx con token inválido")
    void refresh_invalidToken_returnsError() throws Exception {
        String body = """
                {"refreshToken": "este-token-no-existe-en-la-db"}
                """;

        mockMvc.perform(post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is4xxClientError());
    }

    // ─── Logout ──────────────────────────────────────────────────────────────

    @Test
    @Order(7)
    @DisplayName("POST /api/auth/logout → 204 con token inválido (logout es idempotente)")
    void logout_invalidToken_returnsNoContent() throws Exception {
        String body = """
                {"refreshToken": "token-invalido-para-logout"}
                """;

        mockMvc.perform(post("/api/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(8)
    @DisplayName("POST /api/auth/logout → 204 con refreshToken válido")
    void logout_validToken_returns204() throws Exception {
        // Primero hacemos login para obtener un nuevo token
        String loginBody = """
                {"username": "%s", "password": "%s"}
                """.formatted(TEST_USERNAME, TEST_PASSWORD);

        String loginResponse = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginBody))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String refreshToken = objectMapper.readTree(loginResponse).get("refreshToken").asText();

        String logoutBody = """
                {"refreshToken": "%s"}
                """.formatted(refreshToken);

        mockMvc.perform(post("/api/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(logoutBody))
                .andExpect(status().isNoContent());
    }
}
