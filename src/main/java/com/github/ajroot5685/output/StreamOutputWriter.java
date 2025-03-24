package com.github.ajroot5685.output;

import com.github.ajroot5685.component.Default;
import com.github.ajroot5685.exception.StreamOutputException;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.poi.ss.usermodel.Workbook;

public class StreamOutputWriter implements OutputWriter {
    private final OutputStream outputStream;
    private final String fileName;

    public StreamOutputWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.fileName = Default.FILE_NAME;
    }

    public StreamOutputWriter(OutputStream outputStream, String fileName) {
        this.outputStream = outputStream;
        this.fileName = fileName;
    }

    @Override
    public void write(Workbook workbook) {
        try {
            workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            throw new StreamOutputException(e);
        }
    }
}
