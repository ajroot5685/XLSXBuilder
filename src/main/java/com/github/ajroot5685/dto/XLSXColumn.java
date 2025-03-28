package com.github.ajroot5685.dto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a field to be included as a column in the generated Excel file.
 * <br>
 * The optional {@code header} value defines the column name. If not set, the field name is used as the default header.
 * <p>
 * Excel 파일에 포함될 컬럼으로 필드를 표시하는 애노테이션입니다.
 * <br>
 * {@code header} 값은 컬럼 헤더 이름을 지정하며, 설정하지 않으면 필드 이름이 기본값으로 사용됩니다.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface XLSXColumn {

    /**
     * Column header name to be used in the Excel file. If blank, the field name will be used instead.
     * <br>
     * Excel 컬럼에 표시될 헤더 이름입니다. 비워두면 필드명이 기본값으로 사용됩니다.
     *
     * @return the header name
     */
    String header() default "";
}