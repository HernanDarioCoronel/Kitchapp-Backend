package es.coronelhernan.kitchapp.backend.KitchApp.api.controller;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Tax;
import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.TaxUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/taxes")
@RequiredArgsConstructor
public class TaxController {
    private final TaxUseCase useCase;

    @PostMapping
    public ResponseEntity<Tax> create(@RequestBody Tax tax) {
        var result = useCase.save(tax);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tax> findById(@PathVariable UUID id) {
        return useCase.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Tax>> findAll() {
        var result = useCase.findAll();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Tax> update(@PathVariable UUID id, @RequestBody Tax patch) {
        var result = useCase.update(id, patch);
        return ResponseEntity.ok(result);
    }
}

