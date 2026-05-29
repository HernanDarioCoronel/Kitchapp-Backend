package es.coronelhernan.kitchapp.backend.KitchApp.domain.models;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Dish {
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private String description;
    private BigDecimal prepTime;
    private BigDecimal price;
    private Category dishCategory;
    private Boolean isAvailable;
    private String imageUrl;
    private OffsetDateTime createdAt;
    private List<DishIngredient> dishIngredientList;
}