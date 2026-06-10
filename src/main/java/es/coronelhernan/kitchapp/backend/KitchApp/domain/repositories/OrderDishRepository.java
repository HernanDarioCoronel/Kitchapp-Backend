package es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.OrderDish;

import java.util.List;
import java.util.UUID;

public interface OrderDishRepository {
    List<OrderDish> saveAll(List<OrderDish> dishes, UUID orderId);
}
