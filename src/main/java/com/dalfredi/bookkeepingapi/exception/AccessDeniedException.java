package com.dalfredi.bookkeepingapi.exception;

import com.dalfredi.bookkeepingapi.payload.api.ApiResponse;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public class AccessDeniedException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    @Getter
    private final ApiResponse apiResponse;

    public AccessDeniedException(String message) {
        super(message);
        this.apiResponse = new ApiResponse(Boolean.FALSE, message);
    }

    public AccessDeniedException(String action, String resourceName) {
        super();
        String message = StringUtils.capitalize(
            String.format("You don't have permission to %s this %s", action,
                resourceName));
        apiResponse = new ApiResponse(Boolean.FALSE, message);
    }
}