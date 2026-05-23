package es.coronelhernan.kitchapp.backend.KitchApp.api.controller;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Allergen;
import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.AllergenUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/allergens")
@RequiredArgsConstructor
public class AllergenController {
    private final AllergenUseCase useCase;

    @PostMapping
    public ResponseEntity<Allergen> create(@RequestBody Allergen allergen) {
        var result = useCase.save(allergen);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Allergen> findById(
            @PathVariable UUID id) {
        return useCase.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Allergen>> findAll() {
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
    public ResponseEntity<Allergen> update(@PathVariable UUID id, @RequestBody Allergen patch) {
        var result = useCase.update(id, patch);
        return ResponseEntity.ok(result);
    }
}


