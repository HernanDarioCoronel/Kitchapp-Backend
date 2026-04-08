package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.auth.port;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.EmployeeRole;

import java.util.UUID;

public interface AccessTokenPort {
    String generateAccessToken(UUID authUserId, String username, EmployeeRole role);

    long getAccessTokenExpirationSeconds();

    long getRefreshTokenExpirationDays();
}

