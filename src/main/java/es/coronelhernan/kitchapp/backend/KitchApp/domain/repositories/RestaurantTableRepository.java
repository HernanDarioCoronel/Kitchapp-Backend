package es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.RestaurantTable;

import java.util.Optional;
import java.util.UUID;

public interface RestaurantTableRepository {
    Optional<RestaurantTable> findById(UUID id);
}
