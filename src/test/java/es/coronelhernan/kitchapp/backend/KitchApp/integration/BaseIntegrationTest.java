package es.coronelhernan.kitchapp.backend.KitchApp.integration;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.EmployeeRole;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

    protected static final UUID SEED_EMPLOYEE_ADMIN_ID  = UUID.fromString("00000000-0000-0000-0000-000000000501");
    protected static final String SEED_EMPLOYEE_ADMIN_USERNAME = "admin@kitchapp.local";

    protected static final UUID SEED_CATEGORY_INGREDIENT_ID = UUID.fromString("00000000-0000-0000-0000-000000000101");
    protected static final UUID SEED_CATEGORY_PRODUCT_ID    = UUID.fromString("00000000-0000-0000-0000-000000000102");
    protected static final UUID SEED_CATEGORY_DISH_ID       = UUID.fromString("00000000-0000-0000-0000-000000000103");

    protected static final UUID SEED_UNIT_TYPE_UNIDAD_ID = UUID.fromString("00000000-0000-0000-0000-000000000201");
    protected static final UUID SEED_UNIT_TYPE_KG_ID     = UUID.fromString("00000000-0000-0000-0000-000000000202");

    protected static final UUID SEED_TAX_IVA_GENERAL_ID = UUID.fromString("00000000-0000-0000-0000-000000000301");

    protected static final UUID SEED_ALLERGEN_GLUTEN_ID = UUID.fromString("00000000-0000-0000-0000-000000000401");

    protected static final UUID SEED_TABLE_1_ID = UUID.fromString("00000000-0000-0000-0000-000000000601");
    protected static final UUID SEED_TABLE_2_ID = UUID.fromString("00000000-0000-0000-0000-000000000602");

    protected static final UUID SEED_SUPPLIER_FRESCOS_ID = UUID.fromString("00000000-0000-0000-0000-000000000701");

    protected static final UUID SEED_PRODUCT_TOMATE_ID = UUID.fromString("00000000-0000-0000-0000-000000000801");
    protected static final UUID SEED_PRODUCT_AGUA_ID   = UUID.fromString("00000000-0000-0000-0000-000000000805");

    protected static final UUID SEED_DISH_HAMBURGUESA_ID = UUID.fromString("00000000-0000-0000-0000-000000000901");
    protected static final UUID SEED_DISH_ENSALADA_ID    = UUID.fromString("00000000-0000-0000-0000-000000000902");

    protected static final UUID SEED_STOCK_TOMATE_ID = UUID.fromString("00000000-0000-0000-0000-000000001101");

    // Container declared static so @Testcontainers manages its lifecycle (start/stop).
    // We also call start() explicitly in the static block below because in
    // JUnit Jupiter 6 + Spring Boot 4, SpringExtension.beforeAll() (which initialises
    // the Spring context and evaluates @DynamicPropertySource lambdas) runs BEFORE
    // TestcontainersExtension.beforeAll() (which would normally start the container).
    @Container
    @SuppressWarnings("resource")
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("kitchapp_test")
            .withUsername("test")
            .withPassword("test");

    static {
        // Docker Desktop 4.65+ rejects API version < 1.44 (returns HTTP 400).
        // docker-java 3.4.0 defaults to 1.41; the property key is "api.version".
        System.setProperty("api.version", "1.44");
        if (System.getenv("DOCKER_HOST") == null) {
            System.setProperty("testcontainers.docker.host", "tcp://localhost:2375");
        }
        postgres.start();
    }

    @DynamicPropertySource
    static void configureDataSource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",
                () -> postgres.getJdbcUrl() + "?stringtype=unspecified");
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("ENV_DATABASE_NAME", () -> "kitchapp_test");
        registry.add("ENV_POSTGRES_USER", postgres::getUsername);
        registry.add("ENV_POSTGRES_PASSW", postgres::getPassword);
        registry.add("ENV_STORAGE_PATH", () -> "/tmp/kitchapp-test-storage");
        registry.add("ENV_STORAGE_BASE_URL", () -> "http://localhost:8080");
    }

    @Autowired
    private WebApplicationContext context;

    @Autowired
    protected ObjectMapper objectMapper;

    // La instancia es creada aquí y se inicializa en @BeforeEach — Spring Boot 4.0
    // eliminó @AutoConfigureMockMvc; usamos webAppContextSetup que incluye el
    // SecurityFilterChain real (necesario para validar respuestas 401).
    protected MockMvc mockMvc;

    @BeforeEach
    void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Autowired
    private JwtService jwtService;

    protected String adminBearerToken() {
        return "Bearer " + jwtService.generateAccessToken(
                SEED_EMPLOYEE_ADMIN_ID,
                SEED_EMPLOYEE_ADMIN_USERNAME,
                EmployeeRole.ADMIN
        );
    }

    protected String toJson(Object obj) throws JacksonException {
        return objectMapper.writeValueAsString(obj);
    }
}
