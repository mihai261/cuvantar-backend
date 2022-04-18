package com.pad.cuvantar.handlers;

import com.pad.cuvantar.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = {UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String notFoundExceptionHandler(Exception e){
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(value = {SessionException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String badRequestExceptionHandler(Exception e){
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(value = {InvalidCredentialsException.class, InvalidTokenException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String forbiddenExceptionHandler(Exception e){
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(value = {UserAlreadyExistsException.class, EmailAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    String conflictHandler(Exception e){
        return e.getMessage();
    }
}