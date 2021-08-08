package com.treeleaf.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserRegisterStoredException extends RuntimeException{
    public UserRegisterStoredException() {
        super();
    }

    public UserRegisterStoredException(String message) {
        super(message);
    }

    public UserRegisterStoredException(String message, Throwable cause) {
        super(message, cause);
    }
}
