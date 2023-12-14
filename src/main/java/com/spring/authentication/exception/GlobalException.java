package com.spring.authentication.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class GlobalException {
    
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ApiError> handleException(){
        ApiError apiError = new ApiError(400,"User not found",new Date());
        return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
    }
}
