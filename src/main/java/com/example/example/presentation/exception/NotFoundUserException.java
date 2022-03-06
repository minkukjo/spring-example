package com.example.example.presentation.exception;

public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException() {
        super("Not Found User");
    }
}
