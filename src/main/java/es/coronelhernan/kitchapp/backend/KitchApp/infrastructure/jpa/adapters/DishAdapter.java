package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Dish;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.DishRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.DishEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping.DishMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DishAdapter implements DishRepository {
    private final DishEntityRepository jpaRepository;
    private final DishMapper mapper;

    @Override
    public Dish save(Dish domain) {
        var savedEntity = this.jpaRepository.save(this.mapper.toEntity(domain));
        return this.mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Dish> findById(UUID id) {
        return this.jpaRepository.findById(id)
                .map(this.mapper::toDomain);
    }

    @Override
    public List<Dish> findAll() {
        return this.jpaRepository.findAll().stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Dish domain) {
        this.jpaRepository.deleteById(domain.getId());
    }
}

