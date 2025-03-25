package com.github.ajroot5685.example;

import com.github.ajroot5685.dto.XLSXColumn;

public class ExampleRes {

    @XLSXColumn(header = "번호")
    private String no;
    @XLSXColumn(header = "이름")
    private String name;

    public ExampleRes(String no, String name) {
        this.no = no;
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public String getName() {
        return name;
    }
}
