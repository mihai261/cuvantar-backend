package com.pad.cuvantar.handlers;

import com.pad.cuvantar.exceptions.*;
import com.pad.cuvantar.models.RestExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class RestExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = {UserNotFoundException.class, ReviewNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    RestExceptionModel notFoundExceptionHandler(Exception e){
        return new RestExceptionModel(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = {SessionException.class, MissingRequestHeaderException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    RestExceptionModel badRequestExceptionHandler(Exception e){
        return new RestExceptionModel(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = {InvalidCredentialsException.class, InvalidTokenException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    RestExceptionModel forbiddenExceptionHandler(Exception e){
        return new RestExceptionModel(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = {UserAlreadyExistsException.class, EmailAlreadyExistsException.class, ReviewAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    RestExceptionModel conflictHandler(Exception e){
        return new RestExceptionModel(e.getMessage());
    }
}