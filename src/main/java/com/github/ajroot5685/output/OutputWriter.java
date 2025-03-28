package com.github.ajroot5685.output;

import static com.github.ajroot5685.constant.FileConstants.XLSX_EXTENSION;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * Strategy interface for writing Excel {@link Workbook} objects to various outputs.
 * <br>
 * Excel {@link Workbook}을 다양한 출력 대상으로 쓰기 위한 전략 인터페이스입니다.
 */
public interface OutputWriter {

    /**
     * Writes the given {@link Workbook} to the output target.
     * <br><br>
     * 주어진 {@link Workbook}을 출력 대상으로 작성합니다.
     *
     * @param workbook the Excel workbook to write
     *                 <p>
     *                 출력할 Excel {@link Workbook} 객체
     */
    void write(Workbook workbook);

    /**
     * Ensures that the given file name ends with ".xlsx".
     * <br>
     * If not, the extension is automatically appended.
     * <br><br>
     * 주어진 파일 이름이 ".xlsx"로 끝나는지 확인하고, 그렇지 않으면 확장자를 자동으로 추가합니다.
     *
     * @param file the file name to check
     *             <p>
     *             확장자 유효성을 검사할 파일 이름
     * @return the file name ending with ".xlsx"
     * <p>
     * ".xlsx" 확장자가 붙은 파일 이름
     */
    default String ensureXlsxExtension(String file) {
        return file.endsWith(XLSX_EXTENSION) ? file : file + XLSX_EXTENSION;
    }
}