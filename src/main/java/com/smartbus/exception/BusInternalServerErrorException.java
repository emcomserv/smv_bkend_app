package com.smartbus.exception;

public class BusInternalServerErrorException extends RuntimeException {

    private static final long serialVersionUID = 5391047127505940033L;

    public BusInternalServerErrorException() {
        super();
    }

    public BusInternalServerErrorException(final String message) {
        super(message);
    }
}
