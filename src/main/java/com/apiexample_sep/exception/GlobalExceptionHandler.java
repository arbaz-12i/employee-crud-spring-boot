package com.apiexample_sep.exception;

import com.apiexample_sep.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> glogalExceptionHandler(Exception e, WebRequest request){
        //webRequest for url. it will give url description means which url giving exception it automatically captures which url got exception

        ErrorDto dto = new ErrorDto(new Date(),e.getMessage(),request.getDescription(false)); // false means hide the client detail here client is postman
        // false it will give only uri not a client detail if true we got the client detail
        return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);


    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> resourceNotFoundException(ResourceNotFoundException e, WebRequest request){
        ErrorDto dto = new ErrorDto(new Date(),e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static class ResourceNotFoundException extends RuntimeException {

        public ResourceNotFoundException(String msg){
            super(msg);
        }

    }
}
