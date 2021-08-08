package com.treeleaf.blog.advice;

import com.treeleaf.blog.exception.ResourceNotFoundException;
import com.treeleaf.blog.exception.UserAuthorizationException;
import com.treeleaf.blog.exception.UserAlreadyExistException;
import com.treeleaf.blog.util.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistException ex){

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "User already exists.",
                details
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex){

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                "Resource not found",
                details
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAuthorizationException.class)
    public ResponseEntity<Object> handleUserAuthorizationException(UserAuthorizationException ex){

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN,
                "Forbidden",
                details
        );

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }



}
