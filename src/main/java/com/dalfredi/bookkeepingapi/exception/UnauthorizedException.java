package com.dalfredi.bookkeepingapi.exception;

import com.dalfredi.bookkeepingapi.payload.api.ApiResponse;
import lombok.Getter;

public class UnauthorizedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    @Getter
    private final ApiResponse apiResponse;

    public UnauthorizedException(String message) {
        super(message);
        this.apiResponse = new ApiResponse(Boolean.FALSE, message);
    }
}
