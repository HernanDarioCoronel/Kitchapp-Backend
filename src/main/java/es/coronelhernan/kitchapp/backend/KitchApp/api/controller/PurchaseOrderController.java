package es.coronelhernan.kitchapp.backend.KitchApp.api.controller;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.PurchaseOrder;
import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.PurchaseOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/purchase-orders")
@RequiredArgsConstructor
public class PurchaseOrderController {
    private final PurchaseOrderUseCase useCase;

    @PostMapping
    public ResponseEntity<PurchaseOrder> create(@RequestBody PurchaseOrder purchaseOrder) {
        var result = useCase.save(purchaseOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrder> findById(@PathVariable UUID id) {
        return useCase.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PurchaseOrder>> findAll() {
        var result = useCase.findAll();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PurchaseOrder> update(@PathVariable UUID id, @RequestBody PurchaseOrder patch) {
        var result = useCase.update(id, patch);
        return ResponseEntity.ok(result);
    }
}

