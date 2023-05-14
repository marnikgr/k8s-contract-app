package com.uniwa.contract.application.exception;


import java.io.Serial;

public class ApplicationNotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = 1758045387423464742L;

    public ApplicationNotFoundException() {
        super();
    }

    public ApplicationNotFoundException(String message) {
        super(message);
    }
}
