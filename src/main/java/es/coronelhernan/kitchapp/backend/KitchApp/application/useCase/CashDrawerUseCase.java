package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.CashDrawer;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.CashDrawerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import es.coronelhernan.kitchapp.backend.KitchApp.application.utils.PatchUtils;

@RequiredArgsConstructor
@Service
public class CashDrawerUseCase {
    private final CashDrawerRepository repository;

    @Transactional
    public CashDrawer save(CashDrawer cashDrawer) {
        return this.repository.save(cashDrawer.toBuilder().id(null).build());
    }

    @Transactional
    public Optional<CashDrawer> findById(UUID id) {
        return this.repository.findById(id);
    }

    @Transactional
    public List<CashDrawer> findAll() {
        return this.repository.findAll();
    }

    @Transactional
    public void delete(UUID id) {
        var cashDrawer = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Caja no encontrada"));
        this.repository.delete(cashDrawer);
    }

    @Transactional
    public CashDrawer update(UUID id, CashDrawer patch) {
        var cashDrawer = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Caja no encontrada"));
        PatchUtils.copyNonNullProperties(patch, cashDrawer, "id", "createdAt");
        return this.repository.save(cashDrawer);
    }
}

