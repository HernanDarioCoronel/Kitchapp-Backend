package es.coronelhernan.kitchapp.backend.KitchApp.api.controller;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.UnitType;
import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.UnitTypeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/unit-types")
@RequiredArgsConstructor
public class UnitTypeController {
    private final UnitTypeUseCase useCase;

    @PostMapping
    public ResponseEntity<UnitType> create(@RequestBody UnitType unitType) {
        var result = useCase.save(unitType);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitType> findById(@PathVariable UUID id) {
        return useCase.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UnitType>> findAll() {
        var result = useCase.findAll();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UnitType> update(@PathVariable UUID id, @RequestBody UnitType patch) {
        var result = useCase.update(id, patch);
        return ResponseEntity.ok(result);
    }
}

