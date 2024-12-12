package com.bootcamp.social_meli.exception;

import com.bootcamp.social_meli.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleUserNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>(new ExceptionDTO("404", e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
