package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.RestaurantTable;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.RestaurantTableRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.RestaurantTableEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping.RestaurantTableMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RestaurantTableAdapter implements RestaurantTableRepository {
    private final RestaurantTableEntityRepository jpaRepository;
    private final RestaurantTableMapper mapper;

    @Override
    public RestaurantTable save(RestaurantTable domain) {
        var entity = this.mapper.toEntity(domain);
        var savedEntity = this.jpaRepository.save(entity);
        return this.mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<RestaurantTable> findById(UUID id) {
        return this.jpaRepository.findById(id)
                .map(this.mapper::toDomain);
    }

    @Override
    public List<RestaurantTable> findAll() {
        return this.jpaRepository.findAll().stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(RestaurantTable domain) {
        this.jpaRepository.deleteById(domain.getId());
    }
}
