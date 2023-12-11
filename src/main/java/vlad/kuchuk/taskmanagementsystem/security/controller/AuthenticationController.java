package vlad.kuchuk.taskmanagementsystem.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vlad.kuchuk.taskmanagementsystem.security.dto.AuthenticationRequest;
import vlad.kuchuk.taskmanagementsystem.security.dto.AuthenticationResponse;
import vlad.kuchuk.taskmanagementsystem.security.dto.RefreshTokenRequest;
import vlad.kuchuk.taskmanagementsystem.security.service.AuthenticationService;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signing")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<AuthenticationResponse> registerOrAuthenticate(@RequestBody AuthenticationRequest request) {
        return authenticationService.registerOrAuthenticateAsync(request);
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authenticationService.refresh(refreshTokenRequest.getRefreshToken());
    }
}
