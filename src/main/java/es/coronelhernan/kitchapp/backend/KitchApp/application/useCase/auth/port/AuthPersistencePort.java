package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.auth.port;

import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.auth.model.AuthUserSnapshot;
import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.auth.model.RefreshTokenSnapshot;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public interface AuthPersistencePort {
    Optional<AuthUserSnapshot> findActiveUserByUsername(String username);

    Optional<RefreshTokenSnapshot> findActiveRefreshTokenByHash(String tokenHash);

    RefreshTokenSnapshot createRefreshToken(UUID authUserId, String tokenHash, OffsetDateTime expiresAt, OffsetDateTime createdAt);

    void revokeRefreshToken(UUID refreshTokenId, OffsetDateTime revokedAt);

    void linkReplacement(UUID oldRefreshTokenId, UUID newRefreshTokenId);
}

