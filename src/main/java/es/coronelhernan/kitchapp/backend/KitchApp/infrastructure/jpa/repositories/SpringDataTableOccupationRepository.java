package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories;

import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.TableOccupationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataTableOccupationRepository extends JpaRepository<TableOccupationEntity, UUID> {
    Optional<TableOccupationEntity> findByTableIdAndEndedAtIsNull(UUID tableId);
}
