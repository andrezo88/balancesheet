package com.andre.balancesheet.exceptions.service;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message)
    {
        super(message);
    }
}
