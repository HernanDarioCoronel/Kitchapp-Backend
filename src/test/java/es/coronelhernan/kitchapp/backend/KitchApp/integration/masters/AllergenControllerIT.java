package es.coronelhernan.kitchapp.backend.KitchApp.integration.masters;

import es.coronelhernan.kitchapp.backend.KitchApp.integration.BaseIntegrationTest;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests de integración para /api/allergens (maestros).
 *
 * Datos de referencia (V15 + V17 seed):
 *   - Gluten: 00000000-0000-0000-0000-000000000401
 *   - Total de alérgenos sembrados: 9 (4 en V15 + 5 en V17)
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AllergenControllerIT extends BaseIntegrationTest {

    private static String createdAllergenId;

    // ─── GET list ────────────────────────────────────────────────────────────

    @Test
    @Order(1)
    @DisplayName("GET /api/allergens → 200 retorna todos los alérgenos del seed")
    void getAllAllergens_returns200WithSeedData() throws Exception {
        mockMvc.perform(get("/api/allergens")
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(4))))
                .andExpect(jsonPath("$[*].name", hasItem("Gluten")));
    }

    @Test
    @Order(2)
    @DisplayName("GET /api/allergens → 401 sin token")
    void getAllAllergens_withoutToken_returns401() throws Exception {
        mockMvc.perform(get("/api/allergens"))
                .andExpect(status().isUnauthorized());
    }

    // ─── GET by ID ───────────────────────────────────────────────────────────

    @Test
    @Order(3)
    @DisplayName("GET /api/allergens/{id} → 200 con alérgeno existente")
    void getAllergenById_existingId_returns200() throws Exception {
        mockMvc.perform(get("/api/allergens/{id}", SEED_ALLERGEN_GLUTEN_ID)
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(SEED_ALLERGEN_GLUTEN_ID.toString()))
                .andExpect(jsonPath("$.name").value("Gluten"));
    }

    @Test
    @Order(4)
    @DisplayName("GET /api/allergens/{id} → 404 con UUID inexistente")
    void getAllergenById_unknownId_returns404() throws Exception {
        mockMvc.perform(get("/api/allergens/{id}", "00000000-0000-0000-0000-999999999999")
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isNotFound());
    }

    // ─── POST create ─────────────────────────────────────────────────────────

    @Test
    @Order(5)
    @DisplayName("POST /api/allergens → 201 crea nuevo alérgeno")
    void createAllergen_validBody_returns201() throws Exception {
        String body = """
                {
                  "name": "Altramuces IT",
                  "description": "Alérgeno de prueba para integración"
                }
                """;

        String response = mockMvc.perform(post("/api/allergens")
                        .header("Authorization", adminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Altramuces IT"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        createdAllergenId = objectMapper.readTree(response).get("id").asText();
    }

    // ─── PATCH update ────────────────────────────────────────────────────────

    @Test
    @Order(6)
    @DisplayName("PATCH /api/allergens/{id} → 200 actualiza descripción")
    void updateAllergen_validPatch_returns200() throws Exception {
        Assumptions.assumeTrue(createdAllergenId != null);

        String patch = """
                {"description": "Descripción actualizada en test IT"}
                """;

        mockMvc.perform(patch("/api/allergens/{id}", createdAllergenId)
                        .header("Authorization", adminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patch))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Descripción actualizada en test IT"));
    }

    @Test
    @Order(7)
    @DisplayName("PATCH /api/allergens/{id} → 404 con UUID inexistente")
    void updateAllergen_unknownId_returns404() throws Exception {
        mockMvc.perform(patch("/api/allergens/{id}", "00000000-0000-0000-0000-999999999999")
                        .header("Authorization", adminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"X\"}"))
                .andExpect(status().isNotFound());
    }

    // ─── DELETE ──────────────────────────────────────────────────────────────

    @Test
    @Order(8)
    @DisplayName("DELETE /api/allergens/{id} → 204 elimina el alérgeno creado")
    void deleteAllergen_existingId_returns204() throws Exception {
        Assumptions.assumeTrue(createdAllergenId != null);

        mockMvc.perform(delete("/api/allergens/{id}", createdAllergenId)
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(9)
    @DisplayName("GET /api/allergens/{id} → 404 tras eliminarlo")
    void getAllergenById_afterDelete_returns404() throws Exception {
        Assumptions.assumeTrue(createdAllergenId != null);

        mockMvc.perform(get("/api/allergens/{id}", createdAllergenId)
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isNotFound());
    }
}
