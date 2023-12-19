package com.cs790.app.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
        public UserAlreadyExistsException(String message) {
            super(message);
        }
}
