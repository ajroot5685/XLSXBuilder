package com.github.ajroot5685.dto;

import java.util.function.Function;

public record XLSXInfo<T>(
        String header,
        int order,
        Function<T, String> mapper
) {
}
