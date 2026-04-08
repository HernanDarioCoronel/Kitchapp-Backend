package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Dish;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.DishRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DishUseCase {
    private final DishRepository repository;

    @Transactional
    public Dish save(Dish dish) {
        return this.repository.save(dish);
    }

    @Transactional
    public Optional<Dish> findById(UUID id) {
        return this.repository.findById(id);
    }

    @Transactional
    public List<Dish> findAll() {
        return this.repository.findAll();
    }

    @Transactional
    public void delete(UUID id) {
        var dish = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plato no encontrado"));
        this.repository.delete(dish);
    }
}

