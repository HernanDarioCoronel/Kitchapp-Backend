package es.coronelhernan.kitchapp.backend.KitchApp.api.controller;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Stock;
import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.StockUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockController {
    private final StockUseCase useCase;

    @PostMapping
    public ResponseEntity<Stock> create(@RequestBody Stock stock) {
        var result = useCase.save(stock);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> findById(@PathVariable UUID id) {
        return useCase.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Stock>> findAll() {
        var result = useCase.findAll();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Stock> update(@PathVariable UUID id, @RequestBody Stock patch) {
        var result = useCase.update(id, patch);
        return ResponseEntity.ok(result);
    }

}

