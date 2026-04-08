package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.TableOccupation;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.TableOccupationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TableOccupationUseCase {
    private final TableOccupationRepository repository;

    @Transactional
    public TableOccupation save(TableOccupation occupation) {
        return this.repository.save(occupation);
    }

    @Transactional
    public Optional<TableOccupation> findById(UUID id) {
        return this.repository.findById(id);
    }

    @Transactional
    public List<TableOccupation> findAll() {
        return this.repository.findAll();
    }

    @Transactional
    public void delete(UUID id) {
        var occupation = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ocupacion de mesa no encontrada"));
        this.repository.delete(occupation);
    }

    @Transactional
    public Optional<TableOccupation> findOpenByTableId(UUID tableId) {
        return this.repository.findOpenByTableId(tableId);
    }
}

