package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.UnitType;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.UnitTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UnitTypeUseCase {
    private final UnitTypeRepository repository;

    @Transactional
    public UnitType save(UnitType unitType) {
        return this.repository.save(unitType);
    }

    @Transactional
    public Optional<UnitType> findById(UUID id) {
        return this.repository.findById(id);
    }

    @Transactional
    public List<UnitType> findAll() {
        return this.repository.findAll();
    }

    @Transactional
    public void delete(UUID id) {
        var unitType = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de unidad no encontrado"));
        this.repository.delete(unitType);
    }
}

