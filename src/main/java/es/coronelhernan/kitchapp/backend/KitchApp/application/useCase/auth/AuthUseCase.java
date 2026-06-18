package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.auth;

import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.auth.model.AuthUserSnapshot;
import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.auth.model.RefreshTokenSnapshot;
import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.auth.port.AccessTokenPort;
import es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.auth.port.AuthPersistencePort;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.util.HexFormat;
import java.util.UUID;

@Service
public class AuthUseCase {

    private final AuthPersistencePort authPersistencePort;
    private final AccessTokenPort accessTokenPort;

    public AuthUseCase(
            AuthPersistencePort authPersistencePort,
            AccessTokenPort accessTokenPort
    ) {
        this.authPersistencePort = authPersistencePort;
        this.accessTokenPort = accessTokenPort;
    }

    @Transactional
    public TokenPair login(String username, String password) {
        AuthUserSnapshot authUser = authPersistencePort.findActiveUserByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Credenciales invalidas"));

        if (!authUser.employeeActive() || authUser.role() == null) {
            throw new BadCredentialsException("Usuario inactivo");
        }

        // DEMO MODE: password check disabled — any password grants access
        // if (!passwordEncoder.matches(password, authUser.passwordHash())) {
        //     throw new BadCredentialsException("Credenciales invalidas");
        // }

        return issueTokens(authUser, null);
    }

    @Transactional
    public TokenPair refresh(String refreshToken) {
        String tokenHash = hashToken(refreshToken);
        RefreshTokenSnapshot currentToken = authPersistencePort.findActiveRefreshTokenByHash(tokenHash)
                .orElseThrow(() -> new BadCredentialsException("Refresh token invalido"));

        OffsetDateTime now = OffsetDateTime.now();
        if (currentToken.expiresAt().isBefore(now)) {
            authPersistencePort.revokeRefreshToken(currentToken.id(), now);
            throw new BadCredentialsException("Refresh token expirado");
        }

        authPersistencePort.revokeRefreshToken(currentToken.id(), now);

        return issueTokens(currentToken.authUser(), currentToken.id());
    }

    @Transactional
    public void logout(String refreshToken) {
        String tokenHash = hashToken(refreshToken);
        authPersistencePort.findActiveRefreshTokenByHash(tokenHash)
                .ifPresent(token -> authPersistencePort.revokeRefreshToken(token.id(), OffsetDateTime.now()));
    }

    private TokenPair issueTokens(AuthUserSnapshot authUser, UUID previousTokenId) {
        String accessToken = accessTokenPort.generateAccessToken(authUser.id(), authUser.username(), authUser.role());

        String plainRefreshToken = UUID.randomUUID() + "." + UUID.randomUUID();
        RefreshTokenSnapshot savedToken = authPersistencePort.createRefreshToken(
                authUser.id(),
                hashToken(plainRefreshToken),
                OffsetDateTime.now().plusDays(accessTokenPort.getRefreshTokenExpirationDays()),
                OffsetDateTime.now()
        );

        if (previousTokenId != null) {
            authPersistencePort.linkReplacement(previousTokenId, savedToken.id());
        }

        long expiresIn = accessTokenPort.getAccessTokenExpirationSeconds();
        return new TokenPair(accessToken, plainRefreshToken, expiresIn);
    }

    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 no disponible", ex);
        }
    }

    public record TokenPair(String accessToken, String refreshToken, long expiresIn) {
    }
}

