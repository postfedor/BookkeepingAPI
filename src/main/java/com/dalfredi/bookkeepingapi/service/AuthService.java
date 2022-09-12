package com.dalfredi.bookkeepingapi.service;

import com.dalfredi.bookkeepingapi.exception.ApiException;
import com.dalfredi.bookkeepingapi.model.User;
import com.dalfredi.bookkeepingapi.payload.jwt.JwtAuthenticationResponse;
import com.dalfredi.bookkeepingapi.payload.auth.SignInRequest;
import com.dalfredi.bookkeepingapi.payload.auth.SignUpRequest;
import com.dalfredi.bookkeepingapi.repository.UserRepository;
import com.dalfredi.bookkeepingapi.security.JwtAuthentication;
import com.dalfredi.bookkeepingapi.security.JwtProvider;
import io.jsonwebtoken.Claims;
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(SignUpRequest signUpRequest) {
        if (Boolean.TRUE.equals(
            userRepository.existsByUsername(signUpRequest.getUsername()))) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                "Username is already taken");
        }

        if (Boolean.TRUE.equals(
            userRepository.existsByEmail(signUpRequest.getEmail()))) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                "Email is already taken");
        }

        String username = signUpRequest.getUsername().toLowerCase();

        String email = signUpRequest.getEmail().toLowerCase();

        String password = passwordEncoder.encode(signUpRequest.getPassword());

        User user = new User(username, email, password);

        return userRepository.save(user);
    }

    public JwtAuthenticationResponse login(@NonNull SignInRequest authRequest)
        throws AuthException {
        final User user =
            userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new AuthException("User not found"));
        if (passwordEncoder.matches(authRequest.getPassword(),
            user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getUsername(), refreshToken);
            return new JwtAuthenticationResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Incorrect password");
        }
    }

    public JwtAuthenticationResponse getAccessToken(
        @NonNull String refreshToken
    ) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null &&
                saveRefreshToken.equals(refreshToken)) {
                final User user = userRepository.findByUsername(login)
                    .orElseThrow(
                        () -> new AuthException("User not found"));
                final String accessToken =
                    jwtProvider.generateAccessToken(user);
                return new JwtAuthenticationResponse(accessToken, null);
            }
        }
        return new JwtAuthenticationResponse(null, null);
    }

    public JwtAuthenticationResponse refresh(@NonNull String refreshToken)
        throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null &&
                saveRefreshToken.equals(refreshToken)) {
                final User user = userRepository.findByUsername(login)
                    .orElseThrow(
                        () -> new AuthException("User not found"));
                final String accessToken =
                    jwtProvider.generateAccessToken(user);
                final String newRefreshToken =
                    jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getUsername(), newRefreshToken);
                return new JwtAuthenticationResponse(accessToken,
                    newRefreshToken);
            }
        }
        throw new AuthException("Invalid refresh JWT token");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext()
            .getAuthentication();
    }
}