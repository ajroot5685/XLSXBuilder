package com.github.ajroot5685.example;

import jakarta.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;

//@Service
public class ExampleService {

    public void writeExampleExcel(String sheetName, Object req, HttpServletResponse response) {
        // db로부터 조회한 리스트를 엑셀 리스트로 변경(mapper로 분리하기)
        List<ExampleRes> excelDto = List.of(
                new ExampleRes("1", "예시"),
                new ExampleRes("2", "예시2")
        );

//        ExcelBuilder.generate(getHeaderAndMappingFunction(), excelDto, sheetName, outputStream);
    }

    private LinkedHashMap<String, Function<ExampleRes, String>> getHeaderAndMappingFunction() {
        LinkedHashMap<String, Function<ExampleRes, String>> result = new LinkedHashMap<>();
        result.put("No", ExampleRes::getNo);
        result.put("이름", ExampleRes::getName);
        return result;
    }
}
