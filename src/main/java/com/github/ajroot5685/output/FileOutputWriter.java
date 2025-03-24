package com.github.ajroot5685.output;

import com.github.ajroot5685.component.Default;
import com.github.ajroot5685.exception.FileOutputException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import org.apache.poi.ss.usermodel.Workbook;

public class FileOutputWriter implements OutputWriter {
    private final Path filePath;
    private final String fileName;

    public FileOutputWriter(Path filePath) {
        this.filePath = filePath;
        this.fileName = Default.FILE_NAME;
    }

    public FileOutputWriter(Path filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    @Override
    public void write(Workbook workbook) {
        try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
            workbook.write(fos);
        } catch (IOException e) {
            throw new FileOutputException(filePath, e);
        }
    }
}
