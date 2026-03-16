package org.teamscore.individualTask.exceptions;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Hidden
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CannotDeleteEntityException.class)
    public ResponseEntity<?> cannotDeleteEntityExceptionHandler(CannotDeleteEntityException cannotDeleteEntityException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cannotDeleteEntityException.getMessage());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<?> categoryNotFoundExceptionHandler(CategoryNotFoundException categoryNotFoundException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(categoryNotFoundException.getMessage());
    }

    @ExceptionHandler(TypePaymentNotFoundException.class)
    public ResponseEntity<?> typePaymentNotFoundException(TypePaymentNotFoundException typePaymentNotFoundException) {
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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDuplicateName(DataIntegrityViolationException ex) {
        String message = "Ошибка уникальности";

        if (ex.getMessage().contains("category_name_key")) {
            message = "Категория с таким именем уже существует";
        } else if (ex.getMessage().contains("type_payment_name_key")) {
            message = "Тип оплаты с таким именем уже существует";
        }

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(message);
    }
}
