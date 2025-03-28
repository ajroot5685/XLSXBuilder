package com.github.ajroot5685.example.manual.file;

import com.github.ajroot5685.core.ExcelBuilder;
import com.github.ajroot5685.example.manual.ManualExampleDto;
import com.github.ajroot5685.output.OutputWriter;
import com.github.ajroot5685.output.WriterFactory;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;

public class FileOutputExample {
    // Must end with a .xlsx extension.
    // If not, it sticks automatically.
    private final String outputPath = "path/fileName.xlsx";

    public void fileExample() {
        List<ManualExampleDto> excelDto = List.of(
                new ManualExampleDto("1", "example"),
                new ManualExampleDto("2", "example2")
        );

        // Writer generation is required.
        OutputWriter writer = WriterFactory.forFileOutput(outputPath);
        ExcelBuilder.generateWithManualMapping(getHeaderAndMappingFunction(), excelDto, writer);
    }

    private LinkedHashMap<String, Function<ManualExampleDto, String>> getHeaderAndMappingFunction() {
        LinkedHashMap<String, Function<ManualExampleDto, String>> result = new LinkedHashMap<>();
        result.put("No", ManualExampleDto::getNo);
        result.put("이름", ManualExampleDto::getName);
        return result;
    }
}
