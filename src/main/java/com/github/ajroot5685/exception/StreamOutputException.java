package com.github.ajroot5685.exception;

public class StreamOutputException extends RuntimeException {
    public StreamOutputException(Throwable cause) {
        super("Failed to write Excel to output stream", cause);
    }
}
