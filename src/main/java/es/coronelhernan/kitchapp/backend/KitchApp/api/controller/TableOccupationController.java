package es.coronelhernan.kitchapp.backend.KitchApp.api.controller;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.TableOccupation;
import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.TableOccupationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/table-occupations")
@RequiredArgsConstructor
public class TableOccupationController {
    private final TableOccupationUseCase useCase;

    @PostMapping
    public ResponseEntity<TableOccupation> create(@RequestBody TableOccupation occupation) {
        var result = useCase.save(occupation);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TableOccupation> findById(
            @PathVariable UUID id) {
        return useCase.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<TableOccupation>> findAll() {
        var result = useCase.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/tables/{tableId}/open")
    public ResponseEntity<TableOccupation> findOpenByTableId(
            @PathVariable UUID tableId) {
        return useCase.findOpenByTableId(tableId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}


