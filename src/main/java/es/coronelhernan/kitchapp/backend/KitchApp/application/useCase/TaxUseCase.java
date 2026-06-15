package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.Tax;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.TaxRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import es.coronelhernan.kitchapp.backend.KitchApp.application.utils.PatchUtils;

@RequiredArgsConstructor
@Service
public class TaxUseCase {
    private final TaxRepository repository;

    @Transactional
    public Tax save(Tax tax) {
        return this.repository.save(tax.toBuilder().id(null).build());
    }

    @Transactional
    public Optional<Tax> findById(UUID id) {
        return this.repository.findById(id);
    }

    @Transactional
    public List<Tax> findAll() {
        return this.repository.findAll();
    }

    @Transactional
    public void delete(UUID id) {
        var tax = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Impuesto no encontrado"));
        this.repository.delete(tax);
    }

    @Transactional
    public Tax update(UUID id, Tax patch) {
        var tax = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Impuesto no encontrado"));
        PatchUtils.copyNonNullProperties(patch, tax, "id", "createdAt");
        return this.repository.save(tax);
    }
}

