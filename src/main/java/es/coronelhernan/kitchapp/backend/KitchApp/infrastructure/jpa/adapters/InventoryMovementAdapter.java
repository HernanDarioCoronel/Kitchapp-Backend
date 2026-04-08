package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.InventoryMovement;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.InventoryMovementRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.InventoryMovementEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping.InventoryMovementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InventoryMovementAdapter implements InventoryMovementRepository {
    private final InventoryMovementEntityRepository jpaRepository;
    private final InventoryMovementMapper mapper;

    @Override
    public InventoryMovement save(InventoryMovement domain) {
        var savedEntity = this.jpaRepository.save(this.mapper.toEntity(domain));
        return this.mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<InventoryMovement> findById(UUID id) {
        return this.jpaRepository.findById(id)
                .map(this.mapper::toDomain);
    }

    @Override
    public List<InventoryMovement> findAll() {
        return this.jpaRepository.findAll().stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(InventoryMovement domain) {
        this.jpaRepository.deleteById(domain.getId());
    }
}

