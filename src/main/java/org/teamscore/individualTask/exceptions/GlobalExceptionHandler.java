package org.teamscore.individualTask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CannotDeleteEntityException.class)
    public ResponseEntity<?> cannotDeleteEntityExceptionHandler(CannotDeleteEntityException cannotDeleteEntityException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cannotDeleteEntityException.getMessage());
    }
}
