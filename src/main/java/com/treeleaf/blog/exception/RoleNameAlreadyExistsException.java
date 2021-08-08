package com.treeleaf.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RoleNameAlreadyExistsException extends  RuntimeException{

    public RoleNameAlreadyExistsException() {
        super();
    }

    public RoleNameAlreadyExistsException(String message) {
        super(message);
    }

    public RoleNameAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
