package com.amigoscode.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleException(ResourceNotFoundException e,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) {
        ApiError apiError = new ApiError()
                .setPath(request.getRequestURI())
                .setMessage(e.getMessage())
                .setStatusCode(HttpStatus.NOT_FOUND.value())
                .setLocalDateTime(LocalDateTime.now());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(apiError);

    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception e,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) {
        ApiError apiError = new ApiError()
                .setPath(request.getRequestURI())
                .setMessage(e.getMessage())
                .setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setLocalDateTime(LocalDateTime.now());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiError);

    }
    @ExceptionHandler({AccessDeniedException.class, InsufficientAuthenticationException.class})
    public ResponseEntity<ApiError> handleException(InsufficientAuthenticationException e,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) {
        ApiError apiError = new ApiError()
                .setPath(request.getRequestURI())
                .setMessage(e.getMessage())
                .setStatusCode(HttpStatus.FORBIDDEN.value())
                .setLocalDateTime(LocalDateTime.now());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(apiError);

    }

}
