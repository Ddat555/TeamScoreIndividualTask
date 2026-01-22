package org.teamscore.individualTask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CannotDeleteEntityException.class)
    public ResponseEntity<?> cannotDeleteEntityExceptionHandler(CannotDeleteEntityException cannotDeleteEntityException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cannotDeleteEntityException.getMessage());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<?> categoryNotFoundExceptionHandler(CategoryNotFoundException categoryNotFoundException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(categoryNotFoundException.getMessage());
    }

    @ExceptionHandler(TypePaymentNotFoundException.class)
    public ResponseEntity<?> typePaymentNotFoundException(TypePaymentNotFoundException typePaymentNotFoundException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(typePaymentNotFoundException.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
