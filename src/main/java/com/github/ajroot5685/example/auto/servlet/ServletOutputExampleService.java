package com.github.ajroot5685.example.auto.servlet;

import com.github.ajroot5685.core.ExcelBuilder;
import com.github.ajroot5685.example.auto.AutoExampleDto;
import com.github.ajroot5685.output.OutputWriter;
import com.github.ajroot5685.output.WriterFactory;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class ServletOutputExampleService {
    // Must end with a .xlsx extension.
    // If not, it sticks automatically.
    private final String fileName = "fileName.xlsx";

    public void servletExample(HttpServletResponse response) {
        List<AutoExampleDto> excelDto = List.of(
                new AutoExampleDto("1", "example"),
                new AutoExampleDto("2", "example2")
        );

        // Writer generation is required.
        OutputWriter writer = WriterFactory.forServletOutput(response, fileName);
        ExcelBuilder.generateWithAutoMapping(AutoExampleDto.class, excelDto, writer);
    }
}
