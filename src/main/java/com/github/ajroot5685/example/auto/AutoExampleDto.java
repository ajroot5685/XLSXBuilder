package com.github.ajroot5685.example.auto;

import com.github.ajroot5685.dto.XLSXColumn;

public class AutoExampleDto {
    @XLSXColumn(header = "No")
    private final String no;

    @XLSXColumn(header = "name")
    private final String name;

    public AutoExampleDto(String no, String name) {
        this.no = no;
        this.name = name;
    }
}
