package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Supplier;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.SupplierRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SupplierUseCase {
    private final SupplierRepository repository;

    @Transactional
    public Supplier save(Supplier supplier) {
        return this.repository.save(supplier);
    }

    @Transactional
    public Optional<Supplier> findById(UUID id) {
        return this.repository.findById(id);
    }

    @Transactional
    public List<Supplier> findAll() {
        return this.repository.findAll();
    }

    @Transactional
    public void delete(UUID id) {
        var supplier = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado"));
        this.repository.delete(supplier);
    }
}

