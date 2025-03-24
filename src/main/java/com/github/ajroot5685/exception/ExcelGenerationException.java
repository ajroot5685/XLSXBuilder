package com.github.ajroot5685.exception;

public class ExcelGenerationException extends RuntimeException {
    public ExcelGenerationException(Throwable cause) {
        super("There was a problem creating Excel using POI", cause);
    }
}
