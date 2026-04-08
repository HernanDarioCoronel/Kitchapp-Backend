package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.auth.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record RefreshTokenSnapshot(
        UUID id,
        AuthUserSnapshot authUser,
        String tokenHash,
        OffsetDateTime expiresAt,
        OffsetDateTime revokedAt
) {
}

