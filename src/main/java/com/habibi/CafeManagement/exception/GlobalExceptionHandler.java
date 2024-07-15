package com.habibi.CafeManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler  {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object>  handleAnyException(Exception exception, WebRequest request){
        String errorMessageDescription = exception.getLocalizedMessage();
        String errorCause = String.valueOf(exception.getCause());
        if (errorMessageDescription == null) errorMessageDescription = exception.toString();
        ErrorMessage errorMessage = new ErrorMessage(errorMessageDescription, new Date(),errorCause);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<Object>  handleNullPointerException(NullPointerException exception, WebRequest request){
        String errorMessageDescription = exception.getLocalizedMessage();
        String errorCause = String.valueOf(exception.getCause());
        if (errorMessageDescription == null) {
            errorMessageDescription = exception.toString();
        }
        ErrorMessage errorMessage = new ErrorMessage(errorMessageDescription, new Date(), errorCause);
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = HttpClientErrorException.TooManyRequests.class)
    public ResponseEntity<Object>  handleException(HttpClientErrorException.TooManyRequests exception, WebRequest request){
        String errorMessageDescription = exception.getLocalizedMessage();
        String errorCause = String.valueOf(exception.getCause());
        if (errorMessageDescription == null) errorMessageDescription = exception.toString();
        ErrorMessage errorMessage = new ErrorMessage(errorMessageDescription, new Date(), errorCause);
        return new ResponseEntity<>(errorMessage, HttpStatus.TOO_MANY_REQUESTS);
    }


    // TODO: In future below validation code may required.

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        List<ValidationErrorResponse.Violation> violations = ex.getBindingResult().getFieldErrors().stream()
//                .map(fieldError -> new ValidationErrorResponse.Violation(
//                        formatFieldName(fieldError),
//                        fieldError.getDefaultMessage()))
//                .collect(Collectors.toList());
//
//        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
//        errorResponse.setMessage("Validation failed");
//        errorResponse.setViolations(violations);
//
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
//
//    private String formatFieldName(FieldError fieldError) {
//        // Customize field name formatting as needed
//        String[] fieldParts = fieldError.getField().split("\\.");
//        return String.join(" ", fieldParts);
//    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
       ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = formatFieldName((FieldError) error );
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    private String formatFieldName(FieldError fieldError) {
        System.out.println(fieldError.toString());
        // Customize field name formatting as needed
        String[] fieldParts = fieldError.getField().split("\\.");
        return  String.join(" ", fieldParts );
    }
}
