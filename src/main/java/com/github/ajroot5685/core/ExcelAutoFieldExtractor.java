package com.github.ajroot5685.core;

import com.github.ajroot5685.dto.XLSXColumn;
import com.github.ajroot5685.exception.XLSXReflectionException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Utility class that extracts Excel column mapping information from a class using {@link XLSXColumn} annotations via
 * reflection.
 * <br>
 * {@link XLSXColumn} 애노테이션이 붙은 필드를 리플렉션으로 추출하여 Excel 컬럼 매핑 정보를 생성하는 유틸리티 클래스입니다.
 */
public class ExcelAutoFieldExtractor {

    /**
     * Extracts a mapping of header names to value functions from the given class.
     * <br>
     * Only fields annotated with {@link XLSXColumn} are included.
     * <br><br>
     * 주어진 클래스에서 {@link XLSXColumn} 애노테이션이 붙은 필드를 찾아 헤더 이름과 값 추출 함수를 매핑합니다.
     *
     * @param clazz the DTO class with annotated fields
     *              <p>
     *              애노테이션이 붙은 DTO 클래스
     * @param <T>   the DTO type
     * @return a {@link LinkedHashMap} mapping header names to value functions
     * <p>
     * 헤더 이름과 값 추출 함수의 {@link LinkedHashMap}
     */
    public static <T> LinkedHashMap<String, Function<T, String>> extract(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<ColumnInfo<T>> columns = new ArrayList<>();

        for (Field field : fields) {
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

    /**
     * Safely retrieves and converts a field value to string.
     */
    private static <T> String getSafe(Field field, T instance) {
        try {
            Object value = field.get(instance);
            return value != null ? value.toString() : "";
        } catch (Exception e) {
            throw new XLSXReflectionException(field.getName(), e);
        }
    }

    /**
     * Holds header name and value extractor function for a field.
     */
    private record ColumnInfo<T>(
            String header,
            Function<T, String> mapper
    ) {
    }
}
