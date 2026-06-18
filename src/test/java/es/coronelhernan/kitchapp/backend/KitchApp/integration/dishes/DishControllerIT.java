package es.coronelhernan.kitchapp.backend.KitchApp.integration.dishes;

import es.coronelhernan.kitchapp.backend.KitchApp.integration.BaseIntegrationTest;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests de integración para /api/dishes.
 *
 * Datos de referencia (V15 + V17 seed):
 *   - Plato "Hamburguesa de pollo":  00000000-0000-0000-0000-000000000901
 *   - Categoría DISH:                00000000-0000-0000-0000-000000000103
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DishControllerIT extends BaseIntegrationTest {

    private static String createdDishId;

    // ─── GET list ────────────────────────────────────────────────────────────

    @Test
    @Order(1)
    @DisplayName("GET /api/dishes → 200 con lista de platos del seed")
    void getAllDishes_returns200WithSeedData() throws Exception {
        mockMvc.perform(get("/api/dishes")
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))))
                .andExpect(jsonPath("$[*].name", hasItem("Hamburguesa de pollo")));
    }

    @Test
    @Order(2)
    @DisplayName("GET /api/dishes?withIngredients=1 → incluye dishIngredientList")
    void getAllDishesWithIngredients_returnsIngredients() throws Exception {
        mockMvc.perform(get("/api/dishes?withIngredients=1")
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.name=='Hamburguesa de pollo')].dishIngredientList",
                        hasItem(not(empty()))));
    }

    @Test
    @Order(3)
    @DisplayName("GET /api/dishes → 401 sin token")
    void getAllDishes_withoutToken_returns401() throws Exception {
        mockMvc.perform(get("/api/dishes"))
                .andExpect(status().isUnauthorized());
    }

    // ─── GET by ID ───────────────────────────────────────────────────────────

    @Test
    @Order(4)
    @DisplayName("GET /api/dishes/{id} → 200 con plato existente del seed")
    void getDishById_existingId_returns200() throws Exception {
        mockMvc.perform(get("/api/dishes/{id}", SEED_DISH_HAMBURGUESA_ID)
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(SEED_DISH_HAMBURGUESA_ID.toString()))
                .andExpect(jsonPath("$.name").value("Hamburguesa de pollo"))
                .andExpect(jsonPath("$.price").value(9.50));
    }

    @Test
    @Order(5)
    @DisplayName("GET /api/dishes/{id}?withIngredients=1 → incluye ingredientes del plato")
    void getDishById_withIngredients_returnsIngredients() throws Exception {
        mockMvc.perform(get("/api/dishes/{id}?withIngredients=1", SEED_DISH_HAMBURGUESA_ID)
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dishIngredientList", not(empty())));
    }

    @Test
    @Order(6)
    @DisplayName("GET /api/dishes/{id} → 404 con UUID inexistente")
    void getDishById_unknownId_returns404() throws Exception {
        mockMvc.perform(get("/api/dishes/{id}", "00000000-0000-0000-0000-999999999999")
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isNotFound());
    }

    // ─── POST create ─────────────────────────────────────────────────────────

    @Test
    @Order(7)
    @DisplayName("POST /api/dishes → 201 con plato nuevo válido")
    void createDish_validBody_returns201() throws Exception {
        String body = """
                {
                  "name": "Plato de prueba IT",
                  "description": "Descripción de prueba",
                  "prepTime": 12.0,
                  "price": 8.75,
                  "dishCategory": { "id": "%s" },
                  "isAvailable": true
                }
                """.formatted(SEED_CATEGORY_DISH_ID);

        String response = mockMvc.perform(post("/api/dishes")
                        .header("Authorization", adminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Plato de prueba IT"))
                .andExpect(jsonPath("$.price").value(8.75))
                .andReturn()
                .getResponse()
                .getContentAsString();

        createdDishId = objectMapper.readTree(response).get("id").asText();
    }

    @Test
    @Order(8)
    @DisplayName("POST /api/dishes → 401 sin token")
    void createDish_withoutToken_returns401() throws Exception {
        String body = """
                {"name": "Test", "price": 5.0, "isAvailable": true}
                """;

        mockMvc.perform(post("/api/dishes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnauthorized());
    }

    // ─── PATCH update ────────────────────────────────────────────────────────

    @Test
    @Order(9)
    @DisplayName("PATCH /api/dishes/{id} → 200 actualiza nombre y precio")
    void updateDish_validPatch_returns200() throws Exception {
        Assumptions.assumeTrue(createdDishId != null, "Se requiere plato creado en el test anterior");

        String patch = """
                {"name": "Plato de prueba IT (editado)", "price": 9.99}
                """;

        mockMvc.perform(patch("/api/dishes/{id}", createdDishId)
                        .header("Authorization", adminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patch))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Plato de prueba IT (editado)"))
                .andExpect(jsonPath("$.price").value(9.99));
    }

    @Test
    @Order(10)
    @DisplayName("PATCH /api/dishes/{id} → 404 con UUID inexistente")
    void updateDish_unknownId_returns404() throws Exception {
        mockMvc.perform(patch("/api/dishes/{id}", "00000000-0000-0000-0000-999999999999")
                        .header("Authorization", adminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"X\"}"))
                .andExpect(status().isNotFound());
    }

    // ─── DELETE ──────────────────────────────────────────────────────────────

    @Test
    @Order(11)
    @DisplayName("DELETE /api/dishes/{id} → 204 elimina el plato creado")
    void deleteDish_existingId_returns204() throws Exception {
        Assumptions.assumeTrue(createdDishId != null, "Se requiere plato creado en el test anterior");

        mockMvc.perform(delete("/api/dishes/{id}", createdDishId)
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(12)
    @DisplayName("GET /api/dishes/{id} → 404 tras eliminarlo")
    void getDishById_afterDelete_returns404() throws Exception {
        Assumptions.assumeTrue(createdDishId != null, "Se requiere plato eliminado en el test anterior");

        mockMvc.perform(get("/api/dishes/{id}", createdDishId)
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isNotFound());
    }
}
