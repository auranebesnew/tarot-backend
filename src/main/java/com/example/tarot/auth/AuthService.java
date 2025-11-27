package com.example.tarot.auth;

import com.example.tarot.config.JwtService;
import com.example.tarot.user.User;
import com.example.tarot.user.UserRepository;
import com.example.tarot.user.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.BadCredentialsException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Value("${app.security.refresh.expiration-days:30}")
    private long refreshExpirationDays;

    public AuthService(UserRepository userRepository,
                       RefreshTokenRepository refreshTokenRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmailIgnoreCase(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        User user = new User(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getDisplayName()
        );
        user.setRole(UserRole.USER);
        userRepository.save(user);

        return generateTokenPair(user);
    }

    public AuthResponse authenticate(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid credentials");
        }

        User user = userRepository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        return generateTokenPair(user);
    }

    public AuthResponse refresh(RefreshRequest request) {
        RefreshToken stored = refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new BadCredentialsException("Invalid refresh token"));

        if (stored.isRevoked() || stored.getExpiresAt().isBefore(Instant.now())) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        User user = stored.getUser();
        // revoke old token
        stored.revoke();
        refreshTokenRepository.save(stored);

        return generateTokenPair(user);
    }

    private AuthResponse generateTokenPair(User user) {
        String accessToken = jwtService.generateToken(user);

        String refreshTokenValue = UUID.randomUUID().toString();
        Instant expiresAt = Instant.now().plus(refreshExpirationDays, ChronoUnit.DAYS);
        RefreshToken refreshToken = new RefreshToken(refreshTokenValue, user, expiresAt);
        refreshTokenRepository.save(refreshToken);

        return new AuthResponse(accessToken, refreshTokenValue);
    }
}
