package com.github.ajroot5685.output;

import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;

public class WriterFactory {
    public static OutputWriter create(WriterType type, Object target, String fileName) {
        return switch (type) {
            case FILE -> new FileOutputWriter((String) target, fileName);
            case STREAM -> new StreamOutputWriter((OutputStream) target, fileName);
            case SERVLET -> new ServletOutputWriter((HttpServletResponse) target, fileName);
        };
    }
}
