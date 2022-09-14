package com.dalfredi.bookkeepingapi.service;

import static com.dalfredi.bookkeepingapi.utils.Constants.EMAIL;
import static com.dalfredi.bookkeepingapi.utils.Constants.USER;
import static com.dalfredi.bookkeepingapi.utils.Constants.USERNAME;

import com.dalfredi.bookkeepingapi.exception.AccessDeniedException;
import com.dalfredi.bookkeepingapi.exception.AlreadyExistsException;
import com.dalfredi.bookkeepingapi.exception.UnauthorizedException;
import com.dalfredi.bookkeepingapi.model.User;
import com.dalfredi.bookkeepingapi.payload.auth.SignInRequest;
import com.dalfredi.bookkeepingapi.payload.auth.SignUpRequest;
import com.dalfredi.bookkeepingapi.payload.jwt.JwtAuthenticationResponse;
import com.dalfredi.bookkeepingapi.repository.UserRepository;
import com.dalfredi.bookkeepingapi.security.JwtAuthentication;
import com.dalfredi.bookkeepingapi.security.JwtProvider;
import io.jsonwebtoken.Claims;
import java.util.HashMap;
import java.util.Map;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
        String username = signUpRequest.getUsername().toLowerCase();
        String email = signUpRequest.getEmail().toLowerCase();

        if (userRepository.existsByUsername(username)
            .equals(Boolean.TRUE)) {
            throw new AlreadyExistsException(USER, USERNAME, username);
        }

        if (userRepository.existsByEmail(email)
            .equals(Boolean.TRUE)) {
            throw new AlreadyExistsException(USER, EMAIL, email);
        }

        String password = passwordEncoder.encode(signUpRequest.getPassword());

        User user = new User(username, email, password);

        return userRepository.save(user);
    }

    public JwtAuthenticationResponse loginUser(
        @NonNull SignInRequest authRequest) {
        final User user =
            userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new UnauthorizedException("User not found"));
        if (passwordEncoder.matches(authRequest.getPassword(),
            user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getUsername(), refreshToken);
            return new JwtAuthenticationResponse(accessToken, refreshToken);
        } else {
            throw new UnauthorizedException("Incorrect password");
        }
    }

    public JwtAuthenticationResponse getAccessToken(
        @NonNull String refreshToken
    ) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null &&
                saveRefreshToken.equals(refreshToken)) {
                final User user = userRepository.findByUsername(login)
                    .orElseThrow(
                        () -> new UnauthorizedException("User not found"));
                final String accessToken =
                    jwtProvider.generateAccessToken(user);
                return new JwtAuthenticationResponse(accessToken, null);
            }
        }
        throw new UnauthorizedException("Incorrect refresh token");
    }

    public JwtAuthenticationResponse refresh(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String username = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(username);
            if (saveRefreshToken != null &&
                saveRefreshToken.equals(refreshToken)) {
                final User user = userRepository.findByUsername(username)
                    .orElseThrow(
                        () -> new UnauthorizedException("User not found"));
                final String accessToken =
                    jwtProvider.generateAccessToken(user);
                final String newRefreshToken =
                    jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getUsername(), newRefreshToken);
                return new JwtAuthenticationResponse(accessToken,
                    newRefreshToken);
            }
        }
        throw new AccessDeniedException("Invalid refresh JWT token");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext()
            .getAuthentication();
    }
}