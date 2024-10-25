package com.example.e_commerce_app_backend.Exception;

public class UserNotFoundException extends  RuntimeException{
    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
