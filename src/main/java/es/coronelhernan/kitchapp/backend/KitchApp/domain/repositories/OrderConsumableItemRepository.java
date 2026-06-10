package es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.OrderConsumableItem;

import java.util.List;
import java.util.UUID;

public interface OrderConsumableItemRepository {
    List<OrderConsumableItem> saveAll(List<OrderConsumableItem> items, UUID orderId);
}
