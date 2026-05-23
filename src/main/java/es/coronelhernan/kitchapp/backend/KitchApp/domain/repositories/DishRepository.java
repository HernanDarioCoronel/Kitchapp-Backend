package es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Dish;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DishRepository extends BaseRepository<Dish> {
    List<Dish> findAllWithIngredients();
    Optional<Dish> findByIdWithIngredients(UUID id);
}
