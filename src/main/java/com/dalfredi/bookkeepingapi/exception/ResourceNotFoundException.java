package com.dalfredi.bookkeepingapi.exception;

import com.dalfredi.bookkeepingapi.payload.api.ApiResponse;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    @Getter
    private final ApiResponse apiResponse;

    public ResourceNotFoundException(String resourceName, String fieldName,
                                     Object fieldValue) {
        super();
        String message = StringUtils.capitalize(
            String.format("%s not found with %s: '%s'", resourceName, fieldName,
                fieldValue));

        apiResponse = new ApiResponse(Boolean.FALSE, message);
    }
}
