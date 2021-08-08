package com.treeleaf.blog.exception;

public class InvalidImageFormatException extends RuntimeException {

    public InvalidImageFormatException() {
        super();
    }

    public InvalidImageFormatException(String message) {
        super(message);
    }

    public InvalidImageFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
