package es.coronelhernan.kitchapp.backend.KitchApp.domain.repositories;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.models.TableOccupation;

import java.util.Optional;
import java.util.UUID;

public interface TableOccupationRepository {
    Optional<TableOccupation> findOpenByTableId(UUID tableId);

    TableOccupation save(TableOccupation occupation);
}
