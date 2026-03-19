package es.coronelhernan.kitchapp.backend.KitchApp.domain.models;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order {
    @EqualsAndHashCode.Include
    private UUID id;
    private RestaurantTable restaurantTables;
    private Employee employee;
    private OrderStatus status;
    private BigDecimal tip;
    private OffsetDateTime createdAt;
    private OffsetDateTime closedAt;
    private Set<OrderDish> orderDishes;
    private Set<OrderConsumableItem> orderConsumableItems;
}