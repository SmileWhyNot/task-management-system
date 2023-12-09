package vlad.kuchuk.taskmanagementsystem.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vlad.kuchuk.taskmanagementsystem.security.dto.AuthenticationRequest;
import vlad.kuchuk.taskmanagementsystem.security.dto.AuthenticationResponse;
import vlad.kuchuk.taskmanagementsystem.security.entity.Role;
import vlad.kuchuk.taskmanagementsystem.security.entity.UserEntity;
import vlad.kuchuk.taskmanagementsystem.security.exception.RefreshTokenException;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public CompletableFuture<AuthenticationResponse> registerOrAuthenticateAsync(AuthenticationRequest request) {
        Optional<UserEntity> existingUser = userService.findByEmail(request.getEmail());

        return existingUser.map(userEntity ->
                CompletableFuture.completedFuture(authenticate(request, userEntity)))
                .orElseGet(() -> registerAsync(request));
    }

    public AuthenticationResponse refresh(String refreshToken) {
        if (jwtService.isRefreshTokenNotExpired(refreshToken)) {
            Long userId = jwtService.extractId(refreshToken);

            if (userService.isRefreshTokenValid(userId, refreshToken)) {

                String newAccessToken = jwtService.generateToken(userId);
                String newRefreshToken = jwtService.generateRefreshToken(userId);

                userService.updateRefreshToken(userId, newRefreshToken);

                return AuthenticationResponse.builder()
                        .token(newAccessToken)
                        .refreshToken(newRefreshToken)
                        .build();
            } else {
                log.warn("RefreshTokenException");
                throw new RefreshTokenException();
            }
        } else {
            log.warn("RefreshTokenException");
            throw new RefreshTokenException();
        }
    }


    private CompletableFuture<AuthenticationResponse> registerAsync(AuthenticationRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Method to register User started");
            var user = UserEntity.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();

            user = userService.saveUser(user);

            var refreshToken = jwtService.generateRefreshToken(user.getId());
            user.setRefreshToken(refreshToken);
            userService.saveUser(user);

            var jwtToken = jwtService.generateToken(user.getId());

            log.info("Exiting register method");
            return AuthenticationResponse
                    .builder()
                    .token(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        });
    }

    private AuthenticationResponse authenticate(AuthenticationRequest request, UserEntity user) {
        log.info("Method to authenticate User started");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var jwtToken = jwtService.generateToken(user.getId());
        String newRefreshToken = jwtService.generateRefreshToken(user.getId());

        user.setRefreshToken(newRefreshToken);
        userService.saveUser(user);

        log.info("Exiting authenticate method");
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
