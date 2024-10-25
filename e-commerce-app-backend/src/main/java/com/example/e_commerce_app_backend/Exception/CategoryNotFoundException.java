package com.example.e_commerce_app_backend.Exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException() {
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
