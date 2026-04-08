package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.PurchaseOrder;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.PurchaseOrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PurchaseOrderUseCase {
    private final PurchaseOrderRepository repository;

    @Transactional
    public PurchaseOrder save(PurchaseOrder purchaseOrder) {
        return this.repository.save(purchaseOrder);
    }

    @Transactional
    public Optional<PurchaseOrder> findById(UUID id) {
        return this.repository.findById(id);
    }

    @Transactional
    public List<PurchaseOrder> findAll() {
        return this.repository.findAll();
    }

    @Transactional
    public void delete(UUID id) {
        var purchaseOrder = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Orden de compra no encontrada"));
        this.repository.delete(purchaseOrder);
    }
}

