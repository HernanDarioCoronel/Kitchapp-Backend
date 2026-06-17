package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories;

import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.WorkLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkLogEntityRepository
        extends JpaRepository<WorkLogEntity, UUID>, JpaSpecificationExecutor<WorkLogEntity> {
}
