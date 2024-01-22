package com.example.deal.exception;

public class UserException extends RuntimeException{
    public UserException(String errorMessage) {
        super(errorMessage);
    }
}
