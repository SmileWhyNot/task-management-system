package vlad.kuchuk.taskmanagementsystem.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public CompletableFuture<ResponseEntity<AuthenticationResponse>> registerOrAuthenticate(@RequestBody AuthenticationRequest request) {
        return authenticationService.registerOrAuthenticateAsync(request)
                                    .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        AuthenticationResponse response = authenticationService.refresh(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.ok(response);
    }
}
