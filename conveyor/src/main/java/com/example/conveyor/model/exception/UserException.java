package com.example.conveyor.model.exception;

public class UserException extends RuntimeException{
    public UserException(String errorMessage) {
        super(errorMessage);
    }
}
