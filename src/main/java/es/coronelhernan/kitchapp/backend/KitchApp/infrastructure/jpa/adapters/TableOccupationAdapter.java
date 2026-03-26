package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.TableOccupation;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories.TableOccupationRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.TableOccupationEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TableOccupationAdapter implements TableOccupationRepository {

    private final TableOccupationEntityRepository jpaRepository;

    @Override
    public Optional<TableOccupation> findOpenByTableId(UUID tableId) {
        return Optional.empty();
    }

    @Override
    public TableOccupation save(TableOccupation occupation) {
        return null;
    }
}
