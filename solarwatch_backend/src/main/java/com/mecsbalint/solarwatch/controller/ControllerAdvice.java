package com.mecsbalint.solarwatch.controller;

import com.mecsbalint.solarwatch.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.format.DateTimeParseException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ResponseBody
    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidDateExceptionHandler() {
        return "This date String cannot be parsed!";
    }

    @ResponseBody
    @ExceptionHandler(SettlementNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String settlementNotFoundHandler(SettlementNotFoundException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(SettlementAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String settlementAlreadyExistHandler(SettlementAlreadyExistException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(SunriseSunsetAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String sunriseSunsetAlreadyExistHandler(SunriseSunsetAlreadyExistException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(SunriseSunsetNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String sunriseSunsetNotFoundHandler(SunriseSunsetNotFoundException e) {
        return e.getMessage();
    }

}
