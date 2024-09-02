package com.smartbus.exception;

public class BusNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 6676991862642514371L;

    public BusNotFoundException() {
        super();
    }

    public BusNotFoundException(final String message) {
        super(message);
    }
}
