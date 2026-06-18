package es.coronelhernan.kitchapp.backend.KitchApp.integration.products;

import es.coronelhernan.kitchapp.backend.KitchApp.integration.BaseIntegrationTest;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests de integración para /api/products.
 *
 * ProductController usa ProductRequest (DTO) para POST/PATCH.
 * Datos de referencia (V15 seed):
 *   - Producto "Tomate natural" (INGREDIENT): 00000000-0000-0000-0000-000000000801
 *   - Producto "Agua mineral 0,5L" (PRODUCT):  00000000-0000-0000-0000-000000000805
 *   - Categoría INGREDIENT:  00000000-0000-0000-0000-000000000101
 *   - UnitType "Unidad":     00000000-0000-0000-0000-000000000201
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerIT extends BaseIntegrationTest {

    private static String createdProductId;

    // ─── GET list ────────────────────────────────────────────────────────────

    @Test
    @Order(1)
    @DisplayName("GET /api/products → 200 con todos los productos del seed")
    void getAllProducts_returns200WithSeedData() throws Exception {
        mockMvc.perform(get("/api/products")
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(5))))
                .andExpect(jsonPath("$[*].name", hasItem("Tomate natural")));
    }

    @Test
    @Order(2)
    @DisplayName("GET /api/products?type=1 → filtra solo ingredientes (INGREDIENT)")
    void getAllProducts_filterByIngredient_returnsOnlyIngredients() throws Exception {
        mockMvc.perform(get("/api/products?type=1")
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].type", everyItem(equalTo("INGREDIENT"))));
    }

    @Test
    @Order(3)
    @DisplayName("GET /api/products?type=2 → filtra solo productos consumibles (PRODUCT)")
    void getAllProducts_filterByProduct_returnsOnlyProducts() throws Exception {
        mockMvc.perform(get("/api/products?type=2")
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].type", everyItem(equalTo("PRODUCT"))));
    }

    @Test
    @Order(4)
    @DisplayName("GET /api/products → 401 sin token")
    void getAllProducts_withoutToken_returns401() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isUnauthorized());
    }

    // ─── GET by ID ───────────────────────────────────────────────────────────

    @Test
    @Order(5)
    @DisplayName("GET /api/products/{id} → 200 con producto existente del seed")
    void getProductById_existingId_returns200() throws Exception {
        mockMvc.perform(get("/api/products/{id}", SEED_PRODUCT_TOMATE_ID)
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(SEED_PRODUCT_TOMATE_ID.toString()))
                .andExpect(jsonPath("$.name").value("Tomate natural"))
                .andExpect(jsonPath("$.type").value("INGREDIENT"));
    }

    @Test
    @Order(6)
    @DisplayName("GET /api/products/{id} → 404 con UUID inexistente")
    void getProductById_unknownId_returns404() throws Exception {
        mockMvc.perform(get("/api/products/{id}", "00000000-0000-0000-0000-999999999999")
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isNotFound());
    }

    // ─── POST create ─────────────────────────────────────────────────────────

    @Test
    @Order(7)
    @DisplayName("POST /api/products → 201 con ProductRequest válido")
    void createProduct_validRequest_returns201() throws Exception {
        String body = """
                {
                  "sku": "TEST-IT-001",
                  "name": "Producto de prueba IT",
                  "type": "INGREDIENT",
                  "categoryId": "%s",
                  "unitTypeId": "%s",
                  "caloriesPer100g": 50.0,
                  "isActive": true,
                  "allergenIds": []
                }
                """.formatted(SEED_CATEGORY_INGREDIENT_ID, SEED_UNIT_TYPE_KG_ID);

        String response = mockMvc.perform(post("/api/products")
                        .header("Authorization", adminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.sku").value("TEST-IT-001"))
                .andExpect(jsonPath("$.name").value("Producto de prueba IT"))
                .andExpect(jsonPath("$.type").value("INGREDIENT"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        createdProductId = objectMapper.readTree(response).get("id").asText();
    }

    @Test
    @Order(8)
    @DisplayName("POST /api/products → 201 con alérgenos asignados")
    void createProduct_withAllergens_returns201() throws Exception {
        String body = """
                {
                  "sku": "TEST-IT-002",
                  "name": "Producto con alergenos IT",
                  "type": "INGREDIENT",
                  "categoryId": "%s",
                  "unitTypeId": "%s",
                  "caloriesPer100g": 200.0,
                  "isActive": true,
                  "allergenIds": ["%s"]
                }
                """.formatted(SEED_CATEGORY_INGREDIENT_ID, SEED_UNIT_TYPE_KG_ID, SEED_ALLERGEN_GLUTEN_ID);

        String response = mockMvc.perform(post("/api/products")
                        .header("Authorization", adminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.allergenIds", hasItem(SEED_ALLERGEN_GLUTEN_ID.toString())))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Limpieza del producto creado en este test
        String id = objectMapper.readTree(response).get("id").asText();
        mockMvc.perform(delete("/api/products/{id}", id)
                .header("Authorization", adminBearerToken()));
    }

    // ─── PATCH update ────────────────────────────────────────────────────────

    @Test
    @Order(9)
    @DisplayName("PATCH /api/products/{id} → 200 actualiza nombre")
    void updateProduct_validPatch_returns200() throws Exception {
        Assumptions.assumeTrue(createdProductId != null, "Se requiere producto creado anteriormente");

        String patch = """
                {
                  "name": "Producto de prueba IT (editado)",
                  "type": "INGREDIENT",
                  "categoryId": "%s",
                  "unitTypeId": "%s",
                  "isActive": true,
                  "allergenIds": []
                }
                """.formatted(SEED_CATEGORY_INGREDIENT_ID, SEED_UNIT_TYPE_KG_ID);

        mockMvc.perform(patch("/api/products/{id}", createdProductId)
                        .header("Authorization", adminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patch))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Producto de prueba IT (editado)"));
    }

    // ─── DELETE ──────────────────────────────────────────────────────────────

    @Test
    @Order(10)
    @DisplayName("DELETE /api/products/{id} → 204 elimina el producto creado")
    void deleteProduct_existingId_returns204() throws Exception {
        Assumptions.assumeTrue(createdProductId != null, "Se requiere producto creado anteriormente");

        mockMvc.perform(delete("/api/products/{id}", createdProductId)
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(11)
    @DisplayName("GET /api/products/{id} → 404 tras eliminarlo")
    void getProductById_afterDelete_returns404() throws Exception {
        Assumptions.assumeTrue(createdProductId != null, "Se requiere producto eliminado anteriormente");

        mockMvc.perform(get("/api/products/{id}", createdProductId)
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isNotFound());
    }
}
