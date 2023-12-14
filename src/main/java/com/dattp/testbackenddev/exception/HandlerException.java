package com.dattp.testbackenddev.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dattp.testbackenddev.dto.Response;

@RestControllerAdvice
public class HandlerException {
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response hanđleBindException(BindException e){
        return Response.builder()
        .code(HttpStatus.OK.value())
        .message(e.getAllErrors().get(0).getDefaultMessage())
        .build();
    }

    @ExceptionHandler(com.fasterxml.jackson.core.JsonParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response hanđleJsonParseException(Exception e){
        return Response.builder()
        .code(HttpStatus.OK.value())
        .message(e.getMessage())
        .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handlerException(Exception e){
        return Response.builder()
        .code(HttpStatus.OK.value())
        .message(e.getMessage())
        .build();
    }
}
