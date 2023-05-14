package com.uniwa.contract.account.exception;

import java.io.Serial;

public class InvalidAccountCredentials extends Exception {

    @Serial
    private static final long serialVersionUID = -8021487431201323767L;

    public InvalidAccountCredentials() {
        super();
    }

    public InvalidAccountCredentials(String message) {
        super(message);
    }

    public InvalidAccountCredentials(String message, Throwable cause) {
        super(message, cause);
    }
}
