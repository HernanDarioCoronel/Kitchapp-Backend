package es.coronelhernan.kitchapp.backend.KitchApp.api.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank String refreshToken
) {
}

