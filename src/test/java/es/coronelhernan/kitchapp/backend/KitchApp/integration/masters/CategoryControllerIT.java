package es.coronelhernan.kitchapp.backend.KitchApp.integration.masters;

import es.coronelhernan.kitchapp.backend.KitchApp.integration.BaseIntegrationTest;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests de integración para /api/categories (maestros).
 *
 * Datos de referencia (V15 seed):
 *   - Categoría DISH: 00000000-0000-0000-0000-000000000103
 *   - Total categorías sembradas: 3
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryControllerIT extends BaseIntegrationTest {

    private static String createdCategoryId;

    // ─── GET list ────────────────────────────────────────────────────────────

    @Test
    @Order(1)
    @DisplayName("GET /api/categories → 200 con categorías del seed")
    void getAllCategories_returns200WithSeedData() throws Exception {
        mockMvc.perform(get("/api/categories")
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))))
                .andExpect(jsonPath("$[*].type", hasItems("DISH", "PRODUCT", "INGREDIENT")));
    }

    @Test
    @Order(2)
    @DisplayName("GET /api/categories → 401 sin token")
    void getAllCategories_withoutToken_returns401() throws Exception {
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isUnauthorized());
    }

    // ─── GET by ID ───────────────────────────────────────────────────────────

    @Test
    @Order(3)
    @DisplayName("GET /api/categories/{id} → 200 con categoría DISH del seed")
    void getCategoryById_existingId_returns200() throws Exception {
        mockMvc.perform(get("/api/categories/{id}", SEED_CATEGORY_DISH_ID)
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(SEED_CATEGORY_DISH_ID.toString()))
                .andExpect(jsonPath("$.type").value("DISH"))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    @Order(4)
    @DisplayName("GET /api/categories/{id} → 404 con UUID inexistente")
    void getCategoryById_unknownId_returns404() throws Exception {
        mockMvc.perform(get("/api/categories/{id}", "00000000-0000-0000-0000-999999999999")
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isNotFound());
    }

    // ─── POST create ─────────────────────────────────────────────────────────

    @Test
    @Order(5)
    @DisplayName("POST /api/categories → 201 crea categoría de tipo DISH")
    void createCategory_validBody_returns201() throws Exception {
        String body = """
                {
                  "name": "Categoría IT",
                  "description": "Categoría de prueba",
                  "type": "DISH",
                  "active": true
                }
                """;

        String response = mockMvc.perform(post("/api/categories")
                        .header("Authorization", adminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Categoría IT"))
                .andExpect(jsonPath("$.type").value("DISH"))
                .andExpect(jsonPath("$.active").value(true))
                .andReturn()
                .getResponse()
                .getContentAsString();

        createdCategoryId = objectMapper.readTree(response).get("id").asText();
    }

    @Test
    @Order(6)
    @DisplayName("POST /api/categories → 201 crea categoría de tipo INGREDIENT")
    void createCategory_ingredientType_returns201() throws Exception {
        String body = """
                {
                  "name": "Categoría Ingrediente IT",
                  "type": "INGREDIENT",
                  "active": true
                }
                """;

        String response = mockMvc.perform(post("/api/categories")
                        .header("Authorization", adminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("INGREDIENT"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Limpieza
        String id = objectMapper.readTree(response).get("id").asText();
        mockMvc.perform(delete("/api/categories/{id}", id)
                .header("Authorization", adminBearerToken()));
    }

    // ─── PATCH update ────────────────────────────────────────────────────────

    @Test
    @Order(7)
    @DisplayName("PATCH /api/categories/{id} → 200 actualiza nombre")
    void updateCategory_validPatch_returns200() throws Exception {
        Assumptions.assumeTrue(createdCategoryId != null);

        String patch = """
                {"name": "Categoría IT (editada)", "active": false}
                """;

        mockMvc.perform(patch("/api/categories/{id}", createdCategoryId)
                        .header("Authorization", adminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patch))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Categoría IT (editada)"))
                .andExpect(jsonPath("$.active").value(false));
    }

    // ─── DELETE ──────────────────────────────────────────────────────────────

    @Test
    @Order(8)
    @DisplayName("DELETE /api/categories/{id} → 204 elimina la categoría creada")
    void deleteCategory_existingId_returns204() throws Exception {
        Assumptions.assumeTrue(createdCategoryId != null);

        mockMvc.perform(delete("/api/categories/{id}", createdCategoryId)
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(9)
    @DisplayName("GET /api/categories/{id} → 404 tras eliminarla")
    void getCategoryById_afterDelete_returns404() throws Exception {
        Assumptions.assumeTrue(createdCategoryId != null);

        mockMvc.perform(get("/api/categories/{id}", createdCategoryId)
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isNotFound());
    }
}
