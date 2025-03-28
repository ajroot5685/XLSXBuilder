package com.github.ajroot5685.example.manual;

public class ManualExampleDto {
    private final String no;
    private final String name;

    public ManualExampleDto(String no, String name) {
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
