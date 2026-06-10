package es.coronelhernan.kitchapp.backend.KitchApp.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(
        @NotNull UUID tableOccupationId,
        @NotNull UUID employeeId,
        @Valid List<CreateOrderDishRequest> orderDishes,
        @Valid List<CreateOrderConsumableRequest> orderConsumables
) {}
