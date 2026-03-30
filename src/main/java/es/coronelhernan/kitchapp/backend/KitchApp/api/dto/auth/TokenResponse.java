package es.coronelhernan.kitchapp.backend.KitchApp.api.dto.auth;

public record TokenResponse(
        String tokenType,
        String accessToken,
        long expiresIn,
        String refreshToken
) {
}

