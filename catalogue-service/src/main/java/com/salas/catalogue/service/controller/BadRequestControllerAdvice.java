package com.salas.catalogue.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class BadRequestControllerAdvice {

    @Autowired
    private final MessageSource messageSource;

    public BadRequestControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handleBindException(Locale locale, BindException bindException) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                this.messageSource.getMessage("errors.400.title", new Object[0],
                        "errors.400.title", locale));

        Map<String, Object> collect = bindException.getAllErrors().stream()
                .collect(Collectors.toMap(
                        DefaultMessageSourceResolvable::getCode,
                        error -> Objects.requireNonNull(error.getDefaultMessage())
                ));

        problemDetail.setProperties(collect);
        return ResponseEntity.badRequest()
                .body(problemDetail);
    }
}
