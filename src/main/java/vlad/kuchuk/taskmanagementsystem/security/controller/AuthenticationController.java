package vlad.kuchuk.taskmanagementsystem.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "Authentication API",
        description = """
                      Auth API containing register, authenticate and refresh operations\s
                      Register and Authenticate operations combined in one mapping, \s
                      if no such email - register, otherwise - authenticate and validate.\s
                                            
                      This endpoints doesn't need JWT!
                      """
)
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signing")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Register or authenticate mapping",
            description = """
                          Register or authenticate user by email and password\s
                          If user exists - authenticate, otherwise - register\s
                          While authentication - validates password\s
                          If successful - returns access and refresh tokens\s
                          """
    )
    public CompletableFuture<AuthenticationResponse> registerOrAuthenticate(
            @RequestBody AuthenticationRequest request) {
        return authenticationService.registerOrAuthenticateAsync(request);
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Refresh token mapping",
            description = "Using refresh token, returns new access and refresh tokens"
    )
    public AuthenticationResponse refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authenticationService.refresh(refreshTokenRequest.getRefreshToken());
    }
}
