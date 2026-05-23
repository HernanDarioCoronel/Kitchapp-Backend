package es.coronelhernan.kitchapp.backend.KitchApp.api.controller;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Category;
import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.CategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryUseCase useCase;

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
        var result = useCase.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(
            @PathVariable UUID id) {
        return useCase.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        var result = useCase.findAll();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable UUID id, @RequestBody Category patch) {
        var result = useCase.update(id, patch);
        return ResponseEntity.ok(result);
    }
}


