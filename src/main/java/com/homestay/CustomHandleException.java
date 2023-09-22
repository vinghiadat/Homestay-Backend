package com.HomestayManagementSystem;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.HomestayManagementSystem.exception.UsernameAlreadyExistsException;
import com.HomestayManagementSystem.message.ErrorMessage;

@RestControllerAdvice
public class CustomHandleException {
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }
}
