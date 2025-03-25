package com.github.ajroot5685.core;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExcelCreateData {
    private Set<String> headers;
    private List<Map<String, String>> bodies;
    private String title;

    public ExcelCreateData(Set<String> headers, List<Map<String, String>> excelData, String title) {
        this.headers = headers;
        this.bodies = excelData;
        this.title = title;
    }

    public Set<String> getHeaders() {
        return headers;
    }

    public List<Map<String, String>> getBodies() {
        return bodies;
    }

    public String getTitle() {
        return title;
    }
}