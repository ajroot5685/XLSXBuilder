package com.github.ajroot5685.core;

import com.github.ajroot5685.dto.XLSXColumn;
import com.github.ajroot5685.exception.XLSXReflectionException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ExcelAutoFieldExtractor {

    public static <T> LinkedHashMap<String, Function<T, String>> extract(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<ColumnInfo<T>> columns = new ArrayList<>();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (!field.isAnnotationPresent(XLSXColumn.class)) {
                continue;
            }

            field.setAccessible(true);
            XLSXColumn annotation = field.getAnnotation(XLSXColumn.class);

            String header = !annotation.header().isBlank() ? annotation.header() : field.getName();

            columns.add(new ColumnInfo<>(header, instance -> getSafe(field, instance)));
        }

        return columns.stream()
                .collect(Collectors.toMap(
                        ColumnInfo::header,
                        ColumnInfo::mapper,
                        (a, b) -> b,
                        LinkedHashMap::new
                ));
    }

    private static <T> String getSafe(Field field, T instance) {
        try {
            Object value = field.get(instance);
            return value != null ? value.toString() : "";
        } catch (Exception e) {
            throw new XLSXReflectionException(field.getName(), e);
        }
    }

    private record ColumnInfo<T>(
            String header,
            Function<T, String> mapper
    ) {
    }
}
