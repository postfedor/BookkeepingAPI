package com.dalfredi.bookkeepingapi.exception;

import com.dalfredi.bookkeepingapi.payload.api.ApiResponse;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public class AlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    @Getter
    private final ApiResponse apiResponse;

    public AlreadyExistsException(String resourceName, String fieldName,
                                  String resourceValue) {
        super();
        String message = StringUtils.capitalize(
            String.format("%s with %s '%s' already exists", resourceName,
                fieldName, resourceValue));
        this.apiResponse = new ApiResponse(Boolean.FALSE, message);
    }
}