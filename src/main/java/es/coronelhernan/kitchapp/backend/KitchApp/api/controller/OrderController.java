package es.coronelhernan.kitchapp.backend.KitchApp.api.controller;

import es.coronelhernan.kitchapp.backend.KitchApp.api.dto.CreateOrderRequest;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.OrderDishStatus;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.OrderStatus;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Order;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.OrderDish;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.OrderConsumableItem;
import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.OrderUseCase;
import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.CreateOrderWithItemsUseCase;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.OrderDishRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.OrderConsumableItemRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderUseCase useCase;
    private final CreateOrderWithItemsUseCase createOrderWithItemsUseCase;
    private final OrderDishRepository orderDishRepository;
    private final OrderConsumableItemRepository orderConsumableItemRepository;

    @PostMapping
    public ResponseEntity<Order> create(@Valid @RequestBody CreateOrderRequest request) {
        var result = createOrderWithItemsUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(
            @PathVariable UUID id) {
        return useCase.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        var result = useCase.findAll();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable UUID id, @RequestBody Order patch) {
        var result = useCase.update(id, patch);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{orderId}/dishes/{dishId}")
    public ResponseEntity<OrderDish> updateDish(
            @PathVariable UUID orderId,
            @PathVariable UUID dishId,
            @RequestBody OrderDish patch) {
        var result = orderDishRepository.patch(dishId, patch);
        checkAndCompleteOrder(orderId);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{orderId}/consumables/{itemId}")
    public ResponseEntity<OrderConsumableItem> updateConsumable(
            @PathVariable UUID orderId,
            @PathVariable UUID itemId,
            @RequestBody OrderConsumableItem patch) {
        var result = orderConsumableItemRepository.patch(itemId, patch);
        checkAndCompleteOrder(orderId);
        return ResponseEntity.ok(result);
    }

    private void checkAndCompleteOrder(UUID orderId) {
        useCase.findById(orderId).ifPresent(order -> {
            OrderStatus status = order.getStatus();
            if (status == OrderStatus.DONE || status == OrderStatus.DELIVERED || status == OrderStatus.PAID) {
                return;
            }
            Set<OrderDish> dishes = order.getOrderDishes();
            Set<OrderConsumableItem> consumables = order.getOrderConsumableItems();

            boolean allDishesDone = dishes == null || dishes.isEmpty() ||
                    dishes.stream().allMatch(d -> d.getStatus() == OrderDishStatus.DONE);
            boolean allConsumablesDelivered = consumables == null || consumables.isEmpty() ||
                    consumables.stream().allMatch(i -> Boolean.TRUE.equals(i.getDelivered()));

            if (allDishesDone && allConsumablesDelivered) {
                useCase.update(orderId, Order.builder().status(OrderStatus.DONE).build());
            }
        });
    }
}


