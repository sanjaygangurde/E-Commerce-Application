package com.ecommerce.exception;

public class BadApiRequestException extends RuntimeException {


    public BadApiRequestException() {
        super("Bad request....!!");
    }

    public BadApiRequestException(String msg) {
        super(msg);
    }
}
