package com.github.ajroot5685.output;

import com.github.ajroot5685.component.Default;
import com.github.ajroot5685.exception.StreamOutputException;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.apache.poi.ss.usermodel.Workbook;

public class ServletOutputWriter implements OutputWriter {
    private final HttpServletResponse response;
    private final String fileName;

    public ServletOutputWriter(HttpServletResponse response) {
        this.response = response;
        this.fileName = Default.FILE_NAME;
    }

    public ServletOutputWriter(HttpServletResponse response, String fileName) {
        this.response = response;
        this.fileName = fileName;
    }

    @Override
    public void write(Workbook workbook) {
        try {
            String encodedName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedName + "\"");

            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            throw new StreamOutputException(e);
        }
    }
}
