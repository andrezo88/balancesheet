package com.andre.balancesheet.exceptions.service;

public class IdNotFoundException extends RuntimeException{

    public IdNotFoundException(String message)
    {
        super(message);
    }
}
