package es.coronelhernan.kitchapp.backend.KitchApp.api.controller;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Dish;
import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.DishUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/dishes")
@RequiredArgsConstructor
public class DishController {
    private final DishUseCase useCase;

    @PostMapping
    public ResponseEntity<Dish> create(@RequestBody Dish dish) {
        var result = useCase.save(dish);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> findById(@PathVariable UUID id, @RequestParam(name = "withIngredients", defaultValue = "0") int withIngredients) {
        return useCase.findById(id, withIngredients == 1)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Dish>> findAll(
            @RequestParam(name = "withIngredients", defaultValue = "0") int withIngredients) {
        var result = useCase.findAll(withIngredients == 1);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Dish> update(@PathVariable UUID id, @RequestBody Dish patch) {
        var result = useCase.update(id, patch);
        return ResponseEntity.ok(result);
    }
}

