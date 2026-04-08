package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.CashDrawer;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.CashDrawerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CashDrawerUseCase {
    private final CashDrawerRepository repository;

    @Transactional
    public CashDrawer save(CashDrawer cashDrawer) {
        return this.repository.save(cashDrawer);
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
}

