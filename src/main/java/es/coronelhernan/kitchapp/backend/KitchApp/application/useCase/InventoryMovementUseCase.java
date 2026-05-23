package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.InventoryMovement;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.InventoryMovementRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import es.coronelhernan.kitchapp.backend.KitchApp.application.utils.PatchUtils;

@RequiredArgsConstructor
@Service
public class InventoryMovementUseCase {
    private final InventoryMovementRepository repository;

    @Transactional
    public InventoryMovement save(InventoryMovement movement) {
        return this.repository.save(movement);
    }

    @Transactional
    public Optional<InventoryMovement> findById(UUID id) {
        return this.repository.findById(id);
    }

    @Transactional
    public List<InventoryMovement> findAll() {
        return this.repository.findAll();
    }

    @Transactional
    public void delete(UUID id) {
        var movement = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movimiento no encontrado"));
        this.repository.delete(movement);
    }

    @Transactional
    public InventoryMovement update(UUID id, InventoryMovement patch) {
        var movement = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movimiento no encontrado"));
        PatchUtils.copyNonNullProperties(patch, movement, "id", "createdAt");
        return this.repository.save(movement);
    }
}

