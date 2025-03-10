package com.mecsbalint.solarwatch.exceptions;

public class MissingQueryParamException extends RuntimeException {
    public MissingQueryParamException() {
        super("One or more mandatory query parameters are missing!");
    }
}
