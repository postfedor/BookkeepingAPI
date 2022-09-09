package com.dalfredi.bookkeepingapi.controller;

import com.dalfredi.bookkeepingapi.model.User;
import com.dalfredi.bookkeepingapi.payload.ApiResponse;
import com.dalfredi.bookkeepingapi.payload.JwtAuthenticationResponse;
import com.dalfredi.bookkeepingapi.payload.LoginRequest;
import com.dalfredi.bookkeepingapi.payload.RefreshJwtRequest;
import com.dalfredi.bookkeepingapi.payload.SignUpRequest;
import com.dalfredi.bookkeepingapi.service.AuthService;
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
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
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
    public ResponseEntity<JwtAuthenticationResponse> login(
        @RequestBody LoginRequest loginRequest
    ) throws AuthException {
        final JwtAuthenticationResponse token = authService.login(loginRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/token")
    public ResponseEntity<JwtAuthenticationResponse> getNewAccessToken(
        @RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtAuthenticationResponse token =
            authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> getNewRefreshToken(
        @RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtAuthenticationResponse token =
            authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }
}

