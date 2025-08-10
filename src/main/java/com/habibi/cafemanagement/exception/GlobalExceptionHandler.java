package com.habibi.cafemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle all unhandled exceptions
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleAnyException(Exception exception, WebRequest request) {
        String errorMessageDescription = exception.getLocalizedMessage();
        String errorCause = String.valueOf(exception.getCause());

        if (errorMessageDescription == null) {
            errorMessageDescription = exception.toString();
        }

        ErrorMessage errorMessage = new ErrorMessage(
                errorMessageDescription,
                new Date(),
                errorCause
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle NullPointerException specifically
    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException exception, WebRequest request) {
        String errorMessageDescription = exception.getLocalizedMessage();
        String errorCause = String.valueOf(exception.getCause());

        if (errorMessageDescription == null) {
            errorMessageDescription = exception.toString();
        }

        ErrorMessage errorMessage = new ErrorMessage(
                errorMessageDescription,
                new Date(),
                errorCause
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    // Handle validation errors from @Valid annotated request bodies
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = formatFieldName((FieldError) error);
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Handle custom ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                new Date(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                new Date(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, Object> response = new HashMap<>();
        String duplicateValue = extractDuplicateValue(ex.getMessage());

        response.put("message", "Category already exists: \"" + duplicateValue + "\"");
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.CONFLICT.value());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    private String extractDuplicateValue(String errorMessage) {
        // Look for something like: Key (category_name)=(milk shake) already exists
        Pattern pattern = Pattern.compile("\\(category_name\\)=\\((.*?)\\)");
        Matcher matcher = pattern.matcher(errorMessage);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "unknown";
        }
    }

    // Utility method to format field names (for nested fields if needed)
    private String formatFieldName(FieldError fieldError) {
        String[] fieldParts = fieldError.getField().split("\\.");
        return String.join(" ", fieldParts);
    }
}
