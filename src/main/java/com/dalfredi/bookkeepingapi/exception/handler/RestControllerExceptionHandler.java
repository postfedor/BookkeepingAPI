package com.dalfredi.bookkeepingapi.exception.handler;

import com.dalfredi.bookkeepingapi.exception.AccessDeniedException;
import com.dalfredi.bookkeepingapi.exception.AlreadyExistsException;
import com.dalfredi.bookkeepingapi.exception.ResourceNotFoundException;
import com.dalfredi.bookkeepingapi.exception.UnauthorizedException;
import com.dalfredi.bookkeepingapi.payload.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiResponse> resolveException(
        UnauthorizedException exception) {

        ApiResponse apiResponse = exception.getApiResponse();

        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse> resolveException(
        ResourceNotFoundException exception) {
        ApiResponse apiResponse = exception.getApiResponse();

        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ResponseEntity<ApiResponse> resolveException(
        AccessDeniedException exception) {
        ApiResponse apiResponse = exception.getApiResponse();

        return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ResponseEntity<ApiResponse> resolveException(
        AlreadyExistsException exception) {
        ApiResponse apiResponse = exception.getApiResponse();

        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }
}
