package com.github.ajroot5685.exception;

import java.nio.file.Path;

public class FileOutputException extends RuntimeException {
    public FileOutputException(Path filePath, Throwable cause) {
        super("Failed to write Excel file to disk: " + filePath, cause);
    }
}
