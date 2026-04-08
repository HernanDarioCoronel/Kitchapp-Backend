package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.InventoryMovement;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.InventoryMovementRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
}

