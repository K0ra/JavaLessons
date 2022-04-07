package com.luxoft.bankapp.exceptions;

public class ClientExistsException extends RuntimeException {
    public ClientExistsException() {
        super("This client already exists");
    }

    public ClientExistsException(String message) {
        super(message);
    }
}
