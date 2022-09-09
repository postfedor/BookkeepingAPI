package com.dalfredi.bookkeepingapi.payload;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private final String tokenType = "Bearer";
    private String accessToken;
    private String refreshToken;

    public JwtAuthenticationResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
