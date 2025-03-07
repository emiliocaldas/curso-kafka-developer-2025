package com.evaluacion.infrastructure.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.evaluacion.infrastructure.dto.response.CommonErrorResponse;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonErrorResponse handleException(Exception e) {

        log.error("Se produjo una excepcion no controlada: %s".formatted(e.getMessage()), e);

        return CommonErrorResponse.buildErrorResponse();
    }
}
