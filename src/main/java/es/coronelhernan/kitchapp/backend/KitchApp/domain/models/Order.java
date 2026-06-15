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
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order {
    @EqualsAndHashCode.Include
    private UUID id;
    private TableOccupation tableOccupation;
    private Employee employee;
    private OrderStatus status;
    private BigDecimal tip;
    private OffsetDateTime createdAt;
    private OffsetDateTime closedAt;
    private Set<OrderDish> orderDishes;
    private Set<OrderConsumableItem> orderConsumableItems;

    public static Order create(TableOccupation tableOccupation, Employee employee, Set<OrderDish> orderDishSet, Set<OrderConsumableItem> orderConsumableItems) {
        return Order.builder()
                .tableOccupation(tableOccupation)
                .employee(employee)
                .status(OrderStatus.IN_PREPARATION)
                .createdAt(OffsetDateTime.now())
                .orderDishes(orderDishSet)
                .orderConsumableItems(orderConsumableItems)
                .build();
    }

    public void close(BigDecimal tip) {
        this.status = OrderStatus.DONE;
        this.tip = tip;
        this.closedAt = OffsetDateTime.now();
    }

}