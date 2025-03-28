package com.github.ajroot5685.example.auto.stream;

import com.github.ajroot5685.core.ExcelBuilder;
import com.github.ajroot5685.example.auto.AutoExampleDto;
import com.github.ajroot5685.output.OutputWriter;
import com.github.ajroot5685.output.WriterFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class StreamOutputExample {

    public void fileExample() {
        List<AutoExampleDto> excelDto = List.of(
                new AutoExampleDto("1", "example"),
                new AutoExampleDto("2", "example2")
        );

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            // Writer generation is required.
            OutputWriter writer = WriterFactory.forStreamOutput(out);
            ExcelBuilder.generateWithAutoMapping(AutoExampleDto.class, excelDto, writer);

            byte[] excelBytes = out.toByteArray();
            // ex. export to File
            Files.write(Path.of("path/fileName.xlsx"), excelBytes);
        } catch (IOException e) {
            throw new RuntimeException("You control the error.");
        }
    }
}
