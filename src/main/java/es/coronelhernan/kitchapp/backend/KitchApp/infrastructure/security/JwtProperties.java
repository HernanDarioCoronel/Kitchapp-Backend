package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProperties {

    private final String secret;
    private final long accessTokenExpirationMinutes;
    private final long refreshTokenExpirationDays;

    public JwtProperties(
            @Value("${app.security.jwt.secret}") String secret,
            @Value("${app.security.jwt.access-token-expiration-minutes}") long accessTokenExpirationMinutes,
            @Value("${app.security.jwt.refresh-token-expiration-days}") long refreshTokenExpirationDays
    ) {
        this.secret = secret;
        this.accessTokenExpirationMinutes = accessTokenExpirationMinutes;
        this.refreshTokenExpirationDays = refreshTokenExpirationDays;
    }

    public String getSecret() {
        return secret;
    }

    public long getAccessTokenExpirationMinutes() {
        return accessTokenExpirationMinutes;
    }

    public long getRefreshTokenExpirationDays() {
        return refreshTokenExpirationDays;
    }
}

