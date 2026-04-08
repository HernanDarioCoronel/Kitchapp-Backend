package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.adapters;

import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.auth.model.AuthUserSnapshot;
import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.auth.model.RefreshTokenSnapshot;
import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.auth.port.AuthPersistencePort;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.AuthUserEntity;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.RefreshTokenEntity;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.AuthUserEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.RefreshTokenEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthPersistenceAdapter implements AuthPersistencePort {

    private final AuthUserEntityRepository authUserRepository;
    private final RefreshTokenEntityRepository refreshTokenRepository;

    @Override
    public Optional<AuthUserSnapshot> findActiveUserByUsername(String username) {
        return authUserRepository.findByUsernameIgnoreCaseAndIsActiveTrue(username)
                .map(this::toUserSnapshot);
    }

    @Override
    public Optional<RefreshTokenSnapshot> findActiveRefreshTokenByHash(String tokenHash) {
        return refreshTokenRepository.findByTokenHashAndRevokedAtIsNull(tokenHash)
                .map(this::toTokenSnapshot);
    }

    @Override
    public RefreshTokenSnapshot createRefreshToken(UUID authUserId, String tokenHash, OffsetDateTime expiresAt, OffsetDateTime createdAt) {
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setAuthUser(authUserRepository.getReferenceById(authUserId));
        refreshTokenEntity.setTokenHash(tokenHash);
        refreshTokenEntity.setExpiresAt(expiresAt);
        refreshTokenEntity.setRevokedAt(null);
        refreshTokenEntity.setCreatedAt(createdAt);

        return toTokenSnapshot(refreshTokenRepository.save(refreshTokenEntity));
    }

    @Override
    public void revokeRefreshToken(UUID refreshTokenId, OffsetDateTime revokedAt) {
        refreshTokenRepository.findById(refreshTokenId).ifPresent(token -> {
            token.setRevokedAt(revokedAt);
            refreshTokenRepository.save(token);
        });
    }

    @Override
    public void linkReplacement(UUID oldRefreshTokenId, UUID newRefreshTokenId) {
        refreshTokenRepository.findById(oldRefreshTokenId).ifPresent(previousToken -> {
            previousToken.setReplacedBy(refreshTokenRepository.getReferenceById(newRefreshTokenId));
            refreshTokenRepository.save(previousToken);
        });
    }

    private AuthUserSnapshot toUserSnapshot(AuthUserEntity entity) {
        return new AuthUserSnapshot(
                entity.getId(),
                entity.getUsername(),
                entity.getPasswordHash(),
                entity.getEmployee() != null && Boolean.TRUE.equals(entity.getEmployee().getIsActive()),
                entity.getEmployee() == null ? null : entity.getEmployee().getRole()
        );
    }

    private RefreshTokenSnapshot toTokenSnapshot(RefreshTokenEntity entity) {
        return new RefreshTokenSnapshot(
                entity.getId(),
                toUserSnapshot(entity.getAuthUser()),
                entity.getTokenHash(),
                entity.getExpiresAt(),
                entity.getRevokedAt()
        );
    }
}

