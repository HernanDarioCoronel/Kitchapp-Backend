package es.coronelhernan.kitchapp.backend.KitchApp.domain.models;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.ProductType;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {
    @EqualsAndHashCode.Include
    private UUID id;
    private String sku;
    private String name;
    private ProductType type;
    private Category category;
    private UnitType unitType;
    private BigDecimal caloriesPer100g;
    private String imageUrl;
    private Boolean isActive;
    private OffsetDateTime createdAt;
    private Set<Allergen> allergens;
}