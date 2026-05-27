package es.coronelhernan.kitchapp.backend.KitchApp.api.dto.product;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.ProductType;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Category;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.UnitType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String sku,
        String name,
        ProductType type,
        Category category,
        UnitType unitType,
        BigDecimal caloriesPer100g,
        Boolean isActive,
        OffsetDateTime createdAt,
        Set<UUID> allergenIds
) {
}

