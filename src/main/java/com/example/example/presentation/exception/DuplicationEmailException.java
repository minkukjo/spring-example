package com.example.example.presentation.exception;

public class DuplicationEmailException extends RuntimeException {
    public DuplicationEmailException() {
        super("Duplicated Email!");
    }
}
