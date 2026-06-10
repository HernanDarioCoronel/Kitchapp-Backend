package es.coronelhernan.kitchapp.backend.KitchApp.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrderConsumableRequest(
        @NotNull UUID productId,
        @NotNull @Positive Integer count,
        @NotNull @Positive BigDecimal total
) {}
