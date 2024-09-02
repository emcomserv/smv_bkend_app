package com.smartbus.exception;

public class BusBadRequestException extends RuntimeException {

    private static final long serialVersionUID = 3910218446872012353L;

    public BusBadRequestException() {
        super();
    }

    public BusBadRequestException(final String message) {
        super(message);
    }
}
