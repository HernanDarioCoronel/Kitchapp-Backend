package es.coronelhernan.kitchapp.backend.KitchApp.api.controller;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Supplier;
import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.SupplierUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierUseCase useCase;

    @PostMapping
    public ResponseEntity<Supplier> create(@RequestBody Supplier supplier) {
        var result = useCase.save(supplier);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> findById(
            @PathVariable UUID id) {
        return useCase.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Supplier>> findAll() {
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
    public ResponseEntity<Supplier> update(@PathVariable UUID id, @RequestBody Supplier patch) {
        var result = useCase.update(id, patch);
        return ResponseEntity.ok(result);
    }
}


