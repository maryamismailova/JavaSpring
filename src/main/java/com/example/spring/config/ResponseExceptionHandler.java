package com.example.spring.config;

import com.example.spring.exceptions.BadRequestException;
import com.example.spring.exceptions.ExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ExceptionResponse> badRequestException(BadRequestException ex){

        ExceptionResponse response = new ExceptionResponse(ex.getMessage(),"Any details you want to add");

        return new ResponseEntity<>(response,new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

}
