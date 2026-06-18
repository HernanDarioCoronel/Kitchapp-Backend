package es.coronelhernan.kitchapp.backend.KitchApp.integration.orders;

import es.coronelhernan.kitchapp.backend.KitchApp.integration.BaseIntegrationTest;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests de integración para el flujo completo de pedidos:
 *   1. Abrir mesa (POST /api/table-occupations)
 *   2. Crear pedido (POST /api/orders)
 *   3. Consultar pedido (GET /api/orders/{id})
 *   4. Cambiar estado de plato (PATCH /api/orders/{orderId}/dishes/{dishId})
 *   5. Actualizar pedido (PATCH /api/orders/{id})
 *   6. Eliminar pedido (DELETE /api/orders/{id})
 *   7. Cerrar mesa (PATCH /api/table-occupations/{id})
 *
 * Datos de referencia (V15 seed):
 *   - Mesa 2:             00000000-0000-0000-0000-000000000602  (tabla 1 la usan otros tests)
 *   - Empleado admin:     00000000-0000-0000-0000-000000000501
 *   - Plato hamburguesa:  00000000-0000-0000-0000-000000000901
 *   - Producto agua:      00000000-0000-0000-0000-000000000805
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderControllerIT extends BaseIntegrationTest {

    private String occupationId;
    private String orderId;
    private String orderDishId;

    // ─── Apertura de mesa ────────────────────────────────────────────────────

    @Test
    @org.junit.jupiter.api.Order(1)
    @DisplayName("POST /api/table-occupations → 201 abre la mesa")
    void createTableOccupation_returns201() throws Exception {
        String body = """
                {
                  "table": { "id": "%s" }
                }
                """.formatted(SEED_TABLE_2_ID);

        String response = mockMvc.perform(post("/api/table-occupations")
                        .header("Authorization", adminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.status").value("OPEN"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        occupationId = objectMapper.readTree(response).get("id").asText();
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("GET /api/table-occupations/tables/{tableId}/open → 200 retorna la ocupación abierta")
    void getOpenOccupationByTableId_returns200() throws Exception {
        Assumptions.assumeTrue(occupationId != null);

        mockMvc.perform(get("/api/table-occupations/tables/{tableId}/open", SEED_TABLE_2_ID)
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(occupationId))
                .andExpect(jsonPath("$.status").value("OPEN"));
    }

    // ─── Creación de pedido ──────────────────────────────────────────────────

    @Test
    @org.junit.jupiter.api.Order(3)
    @DisplayName("POST /api/orders → 201 crea pedido con plato y consumible")
    void createOrder_validRequest_returns201() throws Exception {
        Assumptions.assumeTrue(occupationId != null);

        String body = """
                {
                  "tableOccupationId": "%s",
                  "employeeId": "%s",
                  "orderDishes": [
                    { "dishId": "%s", "count": 1, "total": 9.50 }
                  ],
                  "orderConsumables": [
                    { "productId": "%s", "count": 2, "total": 3.00 }
                  ]
                }
                """.formatted(occupationId, SEED_EMPLOYEE_ADMIN_ID,
                SEED_DISH_HAMBURGUESA_ID, SEED_PRODUCT_AGUA_ID);

        String response = mockMvc.perform(post("/api/orders")
                        .header("Authorization", adminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.status").value("IN_PREPARATION"))
                .andExpect(jsonPath("$.orderDishes", hasSize(1)))
                .andExpect(jsonPath("$.orderConsumableItems", hasSize(1)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        orderId = objectMapper.readTree(response).get("id").asText();
        orderDishId = objectMapper.readTree(response)
                .get("orderDishes").get(0).get("id").asText();
    }

    // ─── Consulta de pedido ──────────────────────────────────────────────────

    @Test
    @org.junit.jupiter.api.Order(4)
    @DisplayName("GET /api/orders → 200 lista todos los pedidos")
    void getAllOrders_returns200() throws Exception {
        mockMvc.perform(get("/api/orders")
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    @DisplayName("GET /api/orders/{id} → 200 retorna el pedido creado")
    void getOrderById_existingId_returns200() throws Exception {
        Assumptions.assumeTrue(orderId != null);

        mockMvc.perform(get("/api/orders/{id}", orderId)
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId))
                .andExpect(jsonPath("$.status").value("IN_PREPARATION"));
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    @DisplayName("GET /api/orders/{id} → 404 con UUID inexistente")
    void getOrderById_unknownId_returns404() throws Exception {
        mockMvc.perform(get("/api/orders/{id}", "00000000-0000-0000-0000-999999999999")
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isNotFound());
    }

    // ─── Actualización de estado del plato ───────────────────────────────────

    @Test
    @org.junit.jupiter.api.Order(7)
    @DisplayName("PATCH /api/orders/{orderId}/dishes/{dishId} → 200 marca plato como DONE")
    void updateOrderDishStatus_toDone_returns200() throws Exception {
        Assumptions.assumeTrue(orderId != null && orderDishId != null);

        String patch = """
                {"status": "DONE"}
                """;

        mockMvc.perform(patch("/api/orders/{orderId}/dishes/{dishId}", orderId, orderDishId)
                        .header("Authorization", adminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patch))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DONE"));
    }

    // ─── Actualización de estado del pedido ──────────────────────────────────

    @Test
    @org.junit.jupiter.api.Order(8)
    @DisplayName("PATCH /api/orders/{id} → 200 actualiza estado a DELIVERED")
    void updateOrderStatus_toDelivered_returns200() throws Exception {
        Assumptions.assumeTrue(orderId != null);

        String patch = """
                {"status": "DELIVERED"}
                """;

        mockMvc.perform(patch("/api/orders/{id}", orderId)
                        .header("Authorization", adminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patch))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DELIVERED"));
    }

    // ─── Cierre de mesa ──────────────────────────────────────────────────────

    @Test
    @org.junit.jupiter.api.Order(9)
    @DisplayName("PATCH /api/table-occupations/{id} → 200 cierra la mesa")
    void closeTableOccupation_returns200() throws Exception {
        Assumptions.assumeTrue(occupationId != null);

        String patch = """
                {"status": "CLOSED"}
                """;

        mockMvc.perform(patch("/api/table-occupations/{id}", occupationId)
                        .header("Authorization", adminBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patch))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CLOSED"));
    }

    // ─── DELETE pedido ───────────────────────────────────────────────────────

    @Test
    @org.junit.jupiter.api.Order(10)
    @DisplayName("DELETE /api/orders/{id} → 204 elimina el pedido")
    void deleteOrder_existingId_returns204() throws Exception {
        Assumptions.assumeTrue(orderId != null);

        mockMvc.perform(delete("/api/orders/{id}", orderId)
                        .header("Authorization", adminBearerToken()))
                .andExpect(status().isNoContent());
    }

    @Test
    @org.junit.jupiter.api.Order(11)
    @DisplayName("POST /api/orders → 401 sin token")
    void createOrder_withoutToken_returns401() throws Exception {
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isUnauthorized());
    }
}
