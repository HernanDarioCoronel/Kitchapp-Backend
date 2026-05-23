package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Dish;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.DishRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.DishEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.DishIngredientEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping.DishIngredientMapper;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping.DishMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DishAdapter implements DishRepository {
    private final DishEntityRepository jpaRepository;
    private final DishMapper mapper;

    private final DishIngredientEntityRepository dishIngredientJpaRepository;
    private final DishIngredientMapper dishIngredientMapper;

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
    public Optional<Dish> findByIdWithIngredients(UUID id) {
        var maybeDish = this.jpaRepository.findById(id).map(this.mapper::toDomain);
        if (maybeDish.isEmpty()) {
            return Optional.empty();
        }
        var dish = maybeDish.get();

        var ingredientEntities = this.dishIngredientJpaRepository.findByDishIdsWithProductAndDish(java.util.List.of(id));

        var ingDomains = ingredientEntities.stream()
                .map(dishIngredientMapper::toDomain)
                .collect(Collectors.toList());

        var dishWithIngredients = dish.toBuilder()
                .dishIngredientList(ingDomains)
                .build();

        return Optional.of(dishWithIngredients);
    }

    @Override
    public List<Dish> findAll() {
        return this.jpaRepository.findAll().stream()
                .map(this.mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dish> findAllWithIngredients() {
        var dishEntities = this.jpaRepository.findAll();
        var dishes = dishEntities.stream().map(this.mapper::toDomain).collect(Collectors.toList());

        var dishIds = dishes.stream()
                .map(Dish::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (dishIds.isEmpty()) {
            return dishes;
        }

        var ingredientEntities = this.dishIngredientJpaRepository.findByDishIdsWithProductAndDish(dishIds);

        var grouped = ingredientEntities.stream()
                .collect(Collectors.groupingBy(e -> e.getDish().getId()));

        return dishes.stream().map(d -> {
            var ingEntities = grouped.getOrDefault(d.getId(), List.of());
            var ingDomains = ingEntities.stream()
                    .map(dishIngredientMapper::toDomain)
                    .collect(Collectors.toList());
            return d.toBuilder().dishIngredientList(ingDomains).build();
        }).collect(Collectors.toList());
    }

    @Override
    public void delete(Dish domain) {
        this.jpaRepository.deleteById(domain.getId());
    }
}
