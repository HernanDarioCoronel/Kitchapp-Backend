package es.coronelhernan.kitchapp.backend.KitchApp.api.controller;

import es.coronelhernan.kitchapp.backend.KitchApp.api.dto.auth.LoginRequest;
import es.coronelhernan.kitchapp.backend.KitchApp.api.dto.auth.RefreshTokenRequest;
import es.coronelhernan.kitchapp.backend.KitchApp.api.dto.auth.TokenResponse;
import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.auth.AuthUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthUseCase authUseCase;

    public AuthController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthUseCase.TokenPair tokens = authUseCase.login(request.username(), request.password());
        return ResponseEntity.ok(toResponse(tokens));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        AuthUseCase.TokenPair tokens = authUseCase.refresh(request.refreshToken());
        return ResponseEntity.ok(toResponse(tokens));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequest request) {
        authUseCase.logout(request.refreshToken());
        return ResponseEntity.noContent().build();
    }

    private TokenResponse toResponse(AuthUseCase.TokenPair tokens) {
        return new TokenResponse("Bearer", tokens.accessToken(), tokens.expiresIn(), tokens.refreshToken());
    }
}

