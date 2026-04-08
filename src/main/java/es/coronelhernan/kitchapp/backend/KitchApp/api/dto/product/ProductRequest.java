package es.coronelhernan.kitchapp.backend.KitchApp.api.dto.product;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.ProductType;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record ProductRequest(
        String sku,
        String name,
        ProductType type,
        UUID categoryId,
        UUID unitTypeId,
        BigDecimal caloriesPer100g,
        Boolean isActive,
        Set<UUID> allergenIds
) {
}

