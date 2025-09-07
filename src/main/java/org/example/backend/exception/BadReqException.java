package org.example.backend.exception;

public class BadReqException extends RuntimeException {
    public BadReqException(String message) {
        super(message);
    }
}
