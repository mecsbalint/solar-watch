package com.mecsbalint.solarwatch.controller;

import com.mecsbalint.solarwatch.exceptions.MissingQueryParamException;
import com.mecsbalint.solarwatch.exceptions.SettlementNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class SolarWatchControllerAdvice {

    @ResponseBody
    @ExceptionHandler(MissingQueryParamException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidDateExceptionHandler(MissingQueryParamException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(SettlementNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String invalidDateExceptionHandler(SettlementNotFoundException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidDateExceptionHandler() {
        return "This date String cannot be parsed!";
    }
}
