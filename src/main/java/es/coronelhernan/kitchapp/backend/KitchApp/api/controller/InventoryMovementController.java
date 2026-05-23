package es.coronelhernan.kitchapp.backend.KitchApp.api.controller;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.InventoryMovement;
import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.InventoryMovementUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory-movements")
@RequiredArgsConstructor
public class InventoryMovementController {
    private final InventoryMovementUseCase useCase;

    @PostMapping
    public ResponseEntity<InventoryMovement> create(@RequestBody InventoryMovement movement) {
        var result = useCase.save(movement);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryMovement> findById(@PathVariable UUID id) {
        return useCase.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<InventoryMovement>> findAll() {
        var result = useCase.findAll();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<InventoryMovement> update(@PathVariable UUID id, @RequestBody InventoryMovement patch) {
        var result = useCase.update(id, patch);
        return ResponseEntity.ok(result);
    }
}

