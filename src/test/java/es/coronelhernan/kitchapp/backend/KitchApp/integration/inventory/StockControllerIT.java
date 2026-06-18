package es.coronelhernan.kitchapp.backend.KitchApp.integration.inventory;

import es.coronelhernan.kitchapp.backend.KitchApp.integration.BaseIntegrationTest;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests de integración para /api/stock (inventario).
 *
 * Datos de referencia (V15 + V17 seed):
 *   - Stock tomate natural: 00000000-0000-0000-0000-000000001101  (currentQty: 15, minStock: 5)
 *   - Total entradas de stock sembradas: 30
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StockControllerIT extends BaseIntegrationTest {

    private static String createdStockId;

    // ─── GET list ────────────────────────────────────────────────────────────

    @Test
    @Order(1)
    @DisplayName("GET /api/stock → 200 retorna todas las entradas de stock del seed")
    void getAllStock_returns200WithSeedData() throws Exception {
        mockMvc.perform(get("/api/stock")
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(5))));
    }

    @Test
    @Order(2)
    @DisplayName("GET /api/stock → 401 sin token")
    void getAllStock_withoutToken_returns401() throws Exception {
        mockMvc.perform(get("/api/stock"))
                .andExpect(status().isUnauthorized());
    }

    // ─── GET by ID ───────────────────────────────────────────────────────────

    @Test
    @Order(3)
    @DisplayName("GET /api/stock/{id} → 200 con stock del tomate del seed")
    void getStockById_existingId_returns200() throws Exception {
        mockMvc.perform(get("/api/stock/{id}", SEED_STOCK_TOMATE_ID)
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(SEED_STOCK_TOMATE_ID.toString()))
                .andExpect(jsonPath("$.currentQty").value(greaterThan(0.0)))
                .andExpect(jsonPath("$.product.id").value(SEED_PRODUCT_TOMATE_ID.toString()));
    }

    @Test
    @Order(4)
    @DisplayName("GET /api/stock/{id} → 404 con UUID inexistente")
    void getStockById_unknownId_returns404() throws Exception {
        mockMvc.perform(get("/api/stock/{id}", "00000000-0000-0000-0000-999999999999")
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isNotFound());
    }

    // ─── POST create ─────────────────────────────────────────────────────────

    @Test
    @Order(5)
    @DisplayName("POST /api/stock → 201 crea entrada de stock para un producto")
    void createStock_validBody_returns201() throws Exception {
        // Primero creamos un producto nuevo para poder crear stock sin conflicto de unicidad
        String productBody = """
                {
                  "sku": "STOCK-IT-TEST",
                  "name": "Producto para stock IT",
                  "type": "INGREDIENT",
                  "categoryId": "%s",
                  "unitTypeId": "%s",
                  "isActive": true,
                  "allergenIds": []
                }
                """.formatted(SEED_CATEGORY_INGREDIENT_ID, SEED_UNIT_TYPE_KG_ID);

        String productResponse = mockMvc.perform(post("/api/products")
                        .header("Authorization", adminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productBody))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String newProductId = objectMapper.readTree(productResponse).get("id").asText();

        String stockBody = """
                {
                  "product": { "id": "%s" },
                  "currentQty": 20.0,
                  "minStock": 5.0
                }
                """.formatted(newProductId);

        String stockResponse = mockMvc.perform(post("/api/stock")
                        .header("Authorization", adminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stockBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.currentQty").value(20.0))
                .andExpect(jsonPath("$.minStock").value(5.0))
                .andReturn()
                .getResponse()
                .getContentAsString();

        createdStockId = objectMapper.readTree(stockResponse).get("id").asText();
    }

    // ─── PATCH update ────────────────────────────────────────────────────────

    @Test
    @Order(6)
    @DisplayName("PATCH /api/stock/{id} → 200 actualiza cantidad actual")
    void updateStock_validPatch_returns200() throws Exception {
        Assumptions.assumeTrue(createdStockId != null);

        String patch = """
                {"currentQty": 35.5, "minStock": 8.0}
                """;

        mockMvc.perform(patch("/api/stock/{id}", createdStockId)
                        .header("Authorization", adminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patch))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentQty").value(35.5))
                .andExpect(jsonPath("$.minStock").value(8.0));
    }

    @Test
    @Order(7)
    @DisplayName("PATCH /api/stock/{id} (seed) → 200 actualiza stock del tomate")
    void updateSeedStock_validPatch_returns200() throws Exception {
        String patch = """
                {"currentQty": 18.0}
                """;

        mockMvc.perform(patch("/api/stock/{id}", SEED_STOCK_TOMATE_ID)
                        .header("Authorization", adminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patch))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentQty").value(18.0));
    }

    // ─── DELETE ──────────────────────────────────────────────────────────────

    @Test
    @Order(8)
    @DisplayName("DELETE /api/stock/{id} → 204 elimina la entrada de stock creada")
    void deleteStock_existingId_returns204() throws Exception {
        Assumptions.assumeTrue(createdStockId != null);

        mockMvc.perform(delete("/api/stock/{id}", createdStockId)
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isNoContent());
    }
}
