package com.example.example.presentation.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {


    @ExceptionHandler(BindException.class)
    public ResponseEntity<BindingExceptionResponse> handleValid(BindException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        final BindingExceptionResponse bindingExceptionResponse = BindingExceptionResponse.builder()
                .errors(errors)
                .build();
        return new ResponseEntity<>(bindingExceptionResponse, HttpStatus.BAD_REQUEST);

    }
}
