package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.PurchaseOrder;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.PurchaseOrderRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.PurchaseOrderEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.mapping.PurchaseOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PurchaseOrderAdapter implements PurchaseOrderRepository {
    private final PurchaseOrderEntityRepository jpaRepository;
    private final PurchaseOrderMapper mapper;

    @Override
    public PurchaseOrder save(PurchaseOrder domain) {
        var savedEntity = this.jpaRepository.save(this.mapper.toEntity(domain));
        return this.mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<PurchaseOrder> findById(UUID id) {
        return this.jpaRepository.findById(id)
                .map(this.mapper::toDomain);
    }

    @Override
    public List<PurchaseOrder> findAll() {
        return this.jpaRepository.findAll().stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(PurchaseOrder domain) {
        this.jpaRepository.deleteById(domain.getId());
    }
}

