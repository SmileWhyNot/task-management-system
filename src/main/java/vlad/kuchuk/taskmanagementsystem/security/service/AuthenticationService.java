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
import vlad.kuchuk.taskmanagementsystem.security.exception.RefreshTokenException;
import vlad.kuchuk.taskmanagementsystem.user.dto.UserDto;
import vlad.kuchuk.taskmanagementsystem.user.service.UserService;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserService userService;
    private final RefreshService refreshService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public CompletableFuture<AuthenticationResponse> registerOrAuthenticateAsync(AuthenticationRequest request) {
        Optional<UserDto> user = userService.getOptionalByEmail(request.getEmail());

        return user.map(userEntity -> CompletableFuture.completedFuture(authenticate(request, user.get())))
                   .orElseGet(() -> registerAsync(request));
    }

    private AuthenticationResponse authenticate(AuthenticationRequest request, UserDto user) {
        log.info("Method to authenticate User started");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                request.getPassword()));

        var jwtToken = jwtService.generateToken(user.getId());
        String newRefreshToken = jwtService.generateRefreshToken(user.getId());

        user.setRefreshToken(newRefreshToken);
        userService.save(user);

        log.info("Exiting authenticate method");
        return AuthenticationResponse.builder()
                                     .token(jwtToken)
                                     .refreshToken(newRefreshToken)
                                     .build();
    }

    private CompletableFuture<AuthenticationResponse> registerAsync(AuthenticationRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Method to register User started");
            UserDto user = new UserDto().setEmail(request.getEmail())
                                        .setPassword(passwordEncoder.encode(request.getPassword()))
                                        .setRole(Role.USER);

            user = userService.save(user);

            var refreshToken = jwtService.generateRefreshToken(user.getId());
            user.setRefreshToken(refreshToken);
            userService.save(user);

            var jwtToken = jwtService.generateToken(user.getId());

            log.info("Exiting register method");
            return AuthenticationResponse.builder()
                                         .token(jwtToken)
                                         .refreshToken(refreshToken)
                                         .build();
        });
    }

    public AuthenticationResponse refresh(String refreshToken) {
        if (jwtService.isRefreshTokenNotExpired(refreshToken)) {
            Long userId = jwtService.extractId(refreshToken);

            if (refreshService.isRefreshTokenValid(userId, refreshToken)) {

                String newAccessToken = jwtService.generateToken(userId);
                String newRefreshToken = jwtService.generateRefreshToken(userId);

                refreshService.updateRefreshToken(userId, newRefreshToken);

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
}
