package es.coronelhernan.kitchapp.backend.KitchApp.application.useCase.auth;

import es.coronelhernan.kitchapp.backend.KitchApp.domain.enums.EmployeeRole;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.AuthUserEntity;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.entities.RefreshTokenEntity;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.AuthUserEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.jpa.repositories.RefreshTokenEntityRepository;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.security.JwtProperties;
import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.security.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.util.HexFormat;
import java.util.UUID;

@Service
public class AuthUseCase {

    private final AuthUserEntityRepository authUserRepository;
    private final RefreshTokenEntityRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    public AuthUseCase(
            AuthUserEntityRepository authUserRepository,
            RefreshTokenEntityRepository refreshTokenRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            JwtProperties jwtProperties
    ) {
        this.authUserRepository = authUserRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.jwtProperties = jwtProperties;
    }

    @Transactional
    public TokenPair login(String username, String password) {
        AuthUserEntity authUser = authUserRepository.findByUsernameIgnoreCaseAndIsActiveTrue(username)
                .orElseThrow(() -> new BadCredentialsException("Credenciales invalidas"));

        if (authUser.getEmployee() == null || Boolean.FALSE.equals(authUser.getEmployee().getIsActive())) {
            throw new BadCredentialsException("Usuario inactivo");
        }

        if (!passwordEncoder.matches(password, authUser.getPasswordHash())) {
            throw new BadCredentialsException("Credenciales invalidas");
        }

        return issueTokens(authUser, null);
    }

    @Transactional
    public TokenPair refresh(String refreshToken) {
        String tokenHash = hashToken(refreshToken);
        RefreshTokenEntity currentToken = refreshTokenRepository.findByTokenHashAndRevokedAtIsNull(tokenHash)
                .orElseThrow(() -> new BadCredentialsException("Refresh token invalido"));

        if (currentToken.getExpiresAt().isBefore(OffsetDateTime.now())) {
            currentToken.setRevokedAt(OffsetDateTime.now());
            refreshTokenRepository.save(currentToken);
            throw new BadCredentialsException("Refresh token expirado");
        }

        currentToken.setRevokedAt(OffsetDateTime.now());
        refreshTokenRepository.save(currentToken);

        return issueTokens(currentToken.getAuthUser(), currentToken);
    }

    @Transactional
    public void logout(String refreshToken) {
        String tokenHash = hashToken(refreshToken);
        refreshTokenRepository.findByTokenHashAndRevokedAtIsNull(tokenHash)
                .ifPresent(token -> {
                    token.setRevokedAt(OffsetDateTime.now());
                    refreshTokenRepository.save(token);
                });
    }

    private TokenPair issueTokens(AuthUserEntity authUser, RefreshTokenEntity previousToken) {
        EmployeeRole role = authUser.getEmployee().getRole();
        String accessToken = jwtService.generateAccessToken(authUser.getId(), authUser.getUsername(), role);

        String plainRefreshToken = UUID.randomUUID() + "." + UUID.randomUUID();
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setAuthUser(authUser);
        refreshTokenEntity.setTokenHash(hashToken(plainRefreshToken));
        refreshTokenEntity.setExpiresAt(OffsetDateTime.now().plusDays(jwtProperties.getRefreshTokenExpirationDays()));
        refreshTokenEntity.setRevokedAt(null);
        refreshTokenEntity.setCreatedAt(OffsetDateTime.now());

        RefreshTokenEntity savedToken = refreshTokenRepository.save(refreshTokenEntity);

        if (previousToken != null) {
            previousToken.setReplacedBy(savedToken);
            refreshTokenRepository.save(previousToken);
        }

        long expiresIn = jwtProperties.getAccessTokenExpirationMinutes() * 60;
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

