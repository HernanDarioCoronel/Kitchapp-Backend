package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories;

import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.AuthUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthUserEntityRepository extends JpaRepository<AuthUserEntity, UUID> {
    Optional<AuthUserEntity> findByUsernameIgnoreCaseAndIsActiveTrue(String username);
}

