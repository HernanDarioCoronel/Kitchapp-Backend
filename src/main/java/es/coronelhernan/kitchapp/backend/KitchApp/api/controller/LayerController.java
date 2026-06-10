package es.coronelhernan.kitchapp.backend.KitchApp.api.controller;

import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.LayerUseCase;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Layer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/layers")
@RequiredArgsConstructor
public class LayerController {
    private final LayerUseCase useCase;

    @PostMapping
    public ResponseEntity<Layer> create(@RequestBody Layer layer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.save(layer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Layer> findById(@PathVariable UUID id) {
        return useCase.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Layer>> findAll() {
        return ResponseEntity.ok(useCase.findAll());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Layer> update(@PathVariable UUID id, @RequestBody Layer patch) {
        return ResponseEntity.ok(useCase.update(id, patch));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
