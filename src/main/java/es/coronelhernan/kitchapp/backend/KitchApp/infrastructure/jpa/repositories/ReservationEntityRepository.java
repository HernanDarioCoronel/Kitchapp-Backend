package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories;

import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationEntityRepository
    extends JpaRepository<ReservationEntity, UUID> {
}
