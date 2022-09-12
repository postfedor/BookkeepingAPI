package com.dalfredi.bookkeepingapi.controller;

import com.dalfredi.bookkeepingapi.model.User;
import com.dalfredi.bookkeepingapi.payload.api.ApiResponse;
import com.dalfredi.bookkeepingapi.payload.auth.SignInRequest;
import com.dalfredi.bookkeepingapi.payload.auth.SignUpRequest;
import com.dalfredi.bookkeepingapi.payload.jwt.JwtAuthenticationResponse;
import com.dalfredi.bookkeepingapi.payload.jwt.RefreshJwtRequest;
import com.dalfredi.bookkeepingapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import javax.security.auth.message.AuthException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "JWT authorization and authentication")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @SecurityRequirements
    @Operation(summary = "Register new user", description = "Creates new user. Signin to get access and refresh tokens")
    public ResponseEntity<ApiResponse> registerUser(
        @Valid @RequestBody SignUpRequest signUpRequest
    ) {
        User user = authService.registerUser(signUpRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/users/{userId}")
            .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).body(
            new ApiResponse(Boolean.TRUE, "User registered successfully"));
    }

    @PostMapping("/signin")
    @SecurityRequirements
    @Operation(summary = "Sign in with username and password", description = "Once signed in, client should store its refresh and access JWT tokens in order to keep authenticated session alive")
    public ResponseEntity<JwtAuthenticationResponse> login(
        @RequestBody SignInRequest signInRequest
    ) throws AuthException {
        final JwtAuthenticationResponse token =
            authService.login(signInRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/token")
    @SecurityRequirements
    @Operation(summary = "Get access token", description = "Pass refresh token to get a new access one")
    public ResponseEntity<JwtAuthenticationResponse> getNewAccessToken(
        @RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtAuthenticationResponse token =
            authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Get both access and refresh tokens", description = "This safely keeps your authentication alive without signing in")
    public ResponseEntity<JwtAuthenticationResponse> getNewRefreshToken(
        @RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtAuthenticationResponse token =
            authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }
}

