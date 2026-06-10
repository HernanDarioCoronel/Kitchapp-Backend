package es.coronelhernan.kitchapp.backend.KitchApp.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrderDishRequest(
        @NotNull UUID dishId,
        @NotNull @Positive Integer count,
        @NotNull @Positive BigDecimal total
) {}
