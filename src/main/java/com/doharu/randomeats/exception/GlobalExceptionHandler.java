package com.doharu.randomeats.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoMenuFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleNoMenuFound(NoMenuFoundException e) {
        return Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 404,
                "message", e.getMessage()
        );
    }

    @ExceptionHandler(InvalidCategoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleInvalidCategory(InvalidCategoryException e) {
        return Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 400,
                "message", e.getMessage()
        );
    }
}
