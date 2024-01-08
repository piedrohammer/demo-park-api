package com.hammer.demoparkapi.exception;

public class CodigoUniqueViolationException extends RuntimeException {
    public CodigoUniqueViolationException(String message) {
        super(message);
    }
}
