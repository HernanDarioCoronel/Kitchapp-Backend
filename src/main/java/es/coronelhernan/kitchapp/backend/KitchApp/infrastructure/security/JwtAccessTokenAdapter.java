package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.security;

import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.auth.port.AccessTokenPort;
import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.EmployeeRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAccessTokenAdapter implements AccessTokenPort {

    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    @Override
    public String generateAccessToken(UUID authUserId, String username, EmployeeRole role) {
        return jwtService.generateAccessToken(authUserId, username, role);
    }

    @Override
    public long getAccessTokenExpirationSeconds() {
        return jwtProperties.getAccessTokenExpirationMinutes() * 60;
    }

    @Override
    public long getRefreshTokenExpirationDays() {
        return jwtProperties.getRefreshTokenExpirationDays();
    }
}

