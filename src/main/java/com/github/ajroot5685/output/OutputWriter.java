package com.github.ajroot5685.output;

import org.apache.poi.ss.usermodel.Workbook;

public interface OutputWriter {
    void write(Workbook workbook);
}
