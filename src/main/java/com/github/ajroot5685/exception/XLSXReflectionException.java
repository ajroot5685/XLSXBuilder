package com.github.ajroot5685.exception;

public class XLSXReflectionException extends RuntimeException {
    public XLSXReflectionException(String fieldName, Throwable cause) {
        super("Failed to access field: " + fieldName, cause);
    }
}
