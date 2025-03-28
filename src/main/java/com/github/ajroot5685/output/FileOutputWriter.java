package com.github.ajroot5685.output;

import com.github.ajroot5685.exception.FileOutputException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * {@link OutputWriter} implementation that writes an Excel file to a specified file path.
 * <br>
 * If the file name does not end with ".xlsx", the extension is automatically appended.
 * <br><br>
 * 지정된 파일 경로에 Excel 파일을 쓰는 {@link OutputWriter} 구현체입니다.
 * <br>
 * 파일 이름이 ".xlsx"로 끝나지 않으면 자동으로 확장자가 추가됩니다.
 */
public class FileOutputWriter implements OutputWriter {

    private final Path filePath;

    /**
     * Constructs a new {@link FileOutputWriter} with the given file path.
     * <br>
     * If the path does not end with ".xlsx", the extension is automatically added.
     * <br><br>
     * 주어진 파일 경로로 {@link FileOutputWriter}를 생성합니다.
     * <br>
     * 경로가 ".xlsx"로 끝나지 않으면 확장자가 자동으로 붙습니다.
     *
     * @param filePath the target file path (e.g., "output/report.xlsx")
     *                 <p>
     *                 출력할 대상 파일 경로 (예: "output/report.xlsx")
     */
    public FileOutputWriter(String filePath) {
        filePath = ensureXlsxExtension(filePath);
        this.filePath = Path.of(filePath);
    }

    /**
     * Writes the given {@link Workbook} to the specified file path.
     * <br>
     * The output stream is automatically closed after writing.
     * <br><br>
     * 주어진 {@link Workbook}을 지정된 파일 경로에 씁니다.
     * <br>
     * 작성 후 출력 스트림은 자동으로 닫힙니다.
     *
     * @param workbook the workbook to write
     *                 <p>
     *                 출력할 Excel {@link Workbook} 객체
     * @throws FileOutputException if an I/O error occurs during writing
     *                             <p>
     *                             쓰기 도중 입출력 오류가 발생한 경우 {@link FileOutputException}이 발생합니다.
     */
    @Override
    public void write(Workbook workbook) {
        try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
            workbook.write(fos);
        } catch (IOException e) {
            throw new FileOutputException(filePath, e);
        }
    }
}