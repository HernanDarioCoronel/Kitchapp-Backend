package es.coronelhernan.kitchapp.backend.KitchApp.domain.models;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DishIngredient {
    @EqualsAndHashCode.Include
    private UUID id;
    //private Dish dish;
    private Product product;
    private BigDecimal quantity;
    private boolean isOptional;
}