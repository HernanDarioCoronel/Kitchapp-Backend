package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.RestaurantTable;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.RestaurantTableRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.RestaurantTableEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RestaurantTableAdapter implements RestaurantTableRepository {
    private final RestaurantTableEntityRepository jpaRepository;

    @Override
    public Optional<RestaurantTable> findById(UUID id) {
        return Optional.empty();
    }
}
