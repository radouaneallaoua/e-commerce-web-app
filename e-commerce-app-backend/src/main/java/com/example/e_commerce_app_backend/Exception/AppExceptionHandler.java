package com.example.e_commerce_app_backend.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFoundException(UserNotFoundException exception){
        ErrorMessage errorMessage=ErrorMessage.builder()
                .message(exception.getMessage())
                .code(404)
                .timestamp(LocalDateTime.now())
                .build();
        return  new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorMessage> productNotFoundException(ProductNotFoundException exception){
        ErrorMessage errorMessage=ErrorMessage.builder()
                .message(exception.getMessage())
                .code(404)
                .timestamp(LocalDateTime.now())
                .build();
        return  new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorMessage> categoryNotFoundException(CategoryNotFoundException exception){
        ErrorMessage errorMessage=ErrorMessage.builder()
                .message(exception.getMessage())
                .code(404)
                .timestamp(LocalDateTime.now())
                .build();
        return  new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BrandNotFoundException.class)
    public ResponseEntity<ErrorMessage> brandNotFoundException(BrandNotFoundException exception){
        ErrorMessage errorMessage=ErrorMessage.builder()
                .message(exception.getMessage())
                .code(404)
                .timestamp(LocalDateTime.now())
                .build();
        return  new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
