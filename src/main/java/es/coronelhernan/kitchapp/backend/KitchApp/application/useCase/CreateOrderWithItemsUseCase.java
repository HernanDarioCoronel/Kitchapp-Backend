package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.api.dto.CreateOrderConsumableRequest;
import es.coronelhernan.kitchapp.backend.KitchApp.api.dto.CreateOrderDishRequest;
import es.coronelhernan.kitchapp.backend.KitchApp.api.dto.CreateOrderRequest;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.OrderDishStatus;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.*;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateOrderWithItemsUseCase {
    private final OrderRepository orderRepository;
    private final OrderDishRepository orderDishRepository;
    private final OrderConsumableItemRepository orderConsumableItemRepository;
    private final TableOccupationRepository tableOccupationRepository;
    private final EmployeeRepository employeeRepository;
    private final DishRepository dishRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Order execute(CreateOrderRequest request) {
        var tableOccupation = tableOccupationRepository.findById(request.tableOccupationId())
                .orElseThrow(() -> new IllegalArgumentException("Ocupación de mesa no encontrada"));

        var employee = employeeRepository.findById(request.employeeId())
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));

        var dishes = buildOrderDishes(request.orderDishes());
        var consumables = buildOrderConsumables(request.orderConsumables());

        var savedOrder = orderRepository.save(
                Order.create(tableOccupation, employee, new HashSet<>(dishes), new HashSet<>(consumables))
        );

        var savedDishes = request.orderDishes() != null && !request.orderDishes().isEmpty()
                ? orderDishRepository.saveAll(dishes, savedOrder.getId())
                : List.<OrderDish>of();

        var savedConsumables = request.orderConsumables() != null && !request.orderConsumables().isEmpty()
                ? orderConsumableItemRepository.saveAll(consumables, savedOrder.getId())
                : List.<OrderConsumableItem>of();

        return savedOrder.toBuilder()
                .orderDishes(new HashSet<>(savedDishes))
                .orderConsumableItems(new HashSet<>(savedConsumables))
                .build();
    }

    private List<OrderDish> buildOrderDishes(List<CreateOrderDishRequest> requests) {
        if (requests == null || requests.isEmpty()) return List.of();
        return requests.stream()
                .map(req -> {
                    var dish = dishRepository.findById(req.dishId())
                            .orElseThrow(() -> new IllegalArgumentException("Plato no encontrado: " + req.dishId()));
                    return OrderDish.builder()
                            .dish(dish)
                            .count(req.count())
                            .total(req.total())
                            .status(OrderDishStatus.WAITING)
                            .build();
                })
                .toList();
    }

    private List<OrderConsumableItem> buildOrderConsumables(List<CreateOrderConsumableRequest> requests) {
        if (requests == null || requests.isEmpty()) return List.of();
        return requests.stream()
                .map(req -> {
                    var product = productRepository.findById(req.productId())
                            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + req.productId()));
                    return OrderConsumableItem.builder()
                            .product(product)
                            .count(req.count())
                            .total(req.total())
                            .delivered(false)
                            .build();
                })
                .toList();
    }
}
