package com.erdemnayin.expensetrackerapi.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<?> genericException(GenericException e){
        Map<String, String> errors = new HashMap<>();
        errors.put("error",e.getMessage());

        return ResponseEntity
                .status(e.getHttpStatus())
                .body(errors);
    }

    // it is for transactional scenario
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> dataIntegrityViolationException(){

        Map<String, String> errors = new HashMap<>();
        errors.put("error","Throwing exception for demoing rollback! Do Not Worry");

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(errors);

    }
}
