package com.fisa.study.management.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({CustomException.class})
    protected ResponseEntity<?> handleCustomException(CustomException ex){
        return new ResponseEntity<>
                (new ErrorDTO(ex.getErrorCode().getStatus(), ex.getErrorCode().getMessage()), ex.getErrorCode().getStatus());
    }

}
