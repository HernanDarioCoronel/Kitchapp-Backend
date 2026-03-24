package es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Order;

public interface OrderRepository {
    Order save(Order order);
}
