package es.coronelhernan.kitchapp.backend.KitchApp.domain.models;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderDish {
    @EqualsAndHashCode.Include
    private UUID id;
    //private Order order;
    private Dish dish;
    private Integer count;
    private BigDecimal total;
}