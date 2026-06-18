package es.coronelhernan.kitchapp.backend.KitchApp.integration.inventory;

import es.coronelhernan.kitchapp.backend.KitchApp.integration.BaseIntegrationTest;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests de integración para /api/suppliers (proveedores).
 *
 * Datos de referencia (V15 + V17 seed):
 *   - Proveedor "Frescos del Sur": 00000000-0000-0000-0000-000000000701
 *   - Total proveedores sembrados: 6
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SupplierControllerIT extends BaseIntegrationTest {

    private static String createdSupplierId;

    // ─── GET list ────────────────────────────────────────────────────────────

    @Test
    @Order(1)
    @DisplayName("GET /api/suppliers → 200 con proveedores del seed")
    void getAllSuppliers_returns200WithSeedData() throws Exception {
        mockMvc.perform(get("/api/suppliers")
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))))
                .andExpect(jsonPath("$[*].tradeName", hasItem("Frescos del Sur")));
    }

    @Test
    @Order(2)
    @DisplayName("GET /api/suppliers → 401 sin token")
    void getAllSuppliers_withoutToken_returns401() throws Exception {
        mockMvc.perform(get("/api/suppliers"))
                .andExpect(status().isUnauthorized());
    }

    // ─── GET by ID ───────────────────────────────────────────────────────────

    @Test
    @Order(3)
    @DisplayName("GET /api/suppliers/{id} → 200 con proveedor existente")
    void getSupplierById_existingId_returns200() throws Exception {
        mockMvc.perform(get("/api/suppliers/{id}", SEED_SUPPLIER_FRESCOS_ID)
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(SEED_SUPPLIER_FRESCOS_ID.toString()))
                .andExpect(jsonPath("$.tradeName").value("Frescos del Sur"))
                .andExpect(jsonPath("$.nif").value("B12345678"))
                .andExpect(jsonPath("$.type").value("PERISHABLES"));
    }

    @Test
    @Order(4)
    @DisplayName("GET /api/suppliers/{id} → 404 con UUID inexistente")
    void getSupplierById_unknownId_returns404() throws Exception {
        mockMvc.perform(get("/api/suppliers/{id}", "00000000-0000-0000-0000-999999999999")
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isNotFound());
    }

    // ─── POST create ─────────────────────────────────────────────────────────

    @Test
    @Order(5)
    @DisplayName("POST /api/suppliers → 201 crea proveedor válido")
    void createSupplier_validBody_returns201() throws Exception {
        String body = """
                {
                  "nif": "B99000001",
                  "tradeName": "Proveedor IT Test",
                  "businessName": "Proveedor IT Test SL",
                  "reEquivalence": false,
                  "type": "KITCHENWARE",
                  "days": "MON",
                  "email": "it@proveedor-test.local",
                  "phone1": "+34 600 000 001",
                  "iban": "ES22000000000000000099"
                }
                """;

        String response = mockMvc.perform(post("/api/suppliers")
                        .header("Authorization", adminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.tradeName").value("Proveedor IT Test"))
                .andExpect(jsonPath("$.type").value("KITCHENWARE"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        createdSupplierId = objectMapper.readTree(response).get("id").asText();
    }

    // ─── PATCH update ────────────────────────────────────────────────────────

    @Test
    @Order(6)
    @DisplayName("PATCH /api/suppliers/{id} → 200 actualiza email y teléfono")
    void updateSupplier_validPatch_returns200() throws Exception {
        Assumptions.assumeTrue(createdSupplierId != null);

        String patch = """
                {
                  "email": "actualizado@proveedor-test.local",
                  "phone1": "+34 600 000 999"
                }
                """;

        mockMvc.perform(patch("/api/suppliers/{id}", createdSupplierId)
                        .header("Authorization", adminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patch))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("actualizado@proveedor-test.local"));
    }

    @Test
    @Order(7)
    @DisplayName("PATCH /api/suppliers/{id} → 404 con UUID inexistente")
    void updateSupplier_unknownId_returns404() throws Exception {
        mockMvc.perform(patch("/api/suppliers/{id}", "00000000-0000-0000-0000-999999999999")
                        .header("Authorization", adminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tradeName\": \"X\"}"))
                .andExpect(status().isNotFound());
    }

    // ─── DELETE ──────────────────────────────────────────────────────────────

    @Test
    @Order(8)
    @DisplayName("DELETE /api/suppliers/{id} → 204 elimina el proveedor creado")
    void deleteSupplier_existingId_returns204() throws Exception {
        Assumptions.assumeTrue(createdSupplierId != null);

        mockMvc.perform(delete("/api/suppliers/{id}", createdSupplierId)
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(9)
    @DisplayName("GET /api/suppliers/{id} → 404 tras eliminarlo")
    void getSupplierById_afterDelete_returns404() throws Exception {
        Assumptions.assumeTrue(createdSupplierId != null);

        mockMvc.perform(get("/api/suppliers/{id}", createdSupplierId)
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isNotFound());
    }
}
