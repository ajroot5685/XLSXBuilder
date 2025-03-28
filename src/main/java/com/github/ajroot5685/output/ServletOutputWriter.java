package com.github.ajroot5685.output;

import com.github.ajroot5685.constant.Default;
import com.github.ajroot5685.exception.StreamOutputException;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * {@link OutputWriter} implementation for writing Excel files to an HTTP servlet response.
 * <br>
 * Automatically sets content type and content-disposition headers for file download.
 * <br><br>
 * HTTP 서블릿 응답에 Excel 파일을 출력하는 {@link OutputWriter} 구현체입니다.
 * <br>
 * 파일 다운로드를 위한 Content-Type 및 Content-Disposition 헤더를 자동으로 설정합니다.
 */
public class ServletOutputWriter implements OutputWriter {

    private static final String MIME_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final String HEADER_NAME = "Content-Disposition";
    private static final String CONTENT_DISPOSITION_FORMAT = "attachment; filename=\"%s\"";

    private final HttpServletResponse response;
    private final String fileName;

    /**
     * Constructs a new {@link ServletOutputWriter} using the default file name.
     * <br><br>
     * 기본 파일 이름을 사용하는 {@link ServletOutputWriter}를 생성합니다.
     *
     * @param response the HTTP response to write to
     *                 <p>
     *                 Excel 데이터를 쓸 HTTP 응답 객체
     */
    public ServletOutputWriter(HttpServletResponse response) {
        this(response, Default.FILE_NAME);
    }

    /**
     * Constructs a new {@link ServletOutputWriter} with the specified file name.
     * <br>
     * The ".xlsx" extension is appended automatically if missing.
     * <br><br>
     * 지정된 파일 이름으로 {@link ServletOutputWriter}를 생성합니다.
     * <br>
     * 파일 이름에 ".xlsx" 확장자가 없으면 자동으로 추가됩니다.
     *
     * @param response the HTTP response to write to
     *                 <p>
     *                 Excel 데이터를 쓸 HTTP 응답 객체
     * @param fileName the download file name
     *                 <p>
     *                 다운로드될 파일 이름 (예: "report.xlsx")
     */
    public ServletOutputWriter(HttpServletResponse response, String fileName) {
        this.response = response;
        fileName = ensureXlsxExtension(fileName);
        this.fileName = fileName;
    }

    /**
     * Writes the given {@link Workbook} to the servlet response output stream.
     * <br>
     * Sets the appropriate headers for file download. Does <strong>not</strong> flush or close the stream.
     * <br><br>
     * 주어진 {@link Workbook}을 서블릿 응답 스트림에 작성합니다.
     * <br>
     * 파일 다운로드를 위한 헤더를 설정하며, flush나 close는 수행하지 않습니다.
     *
     * @param workbook the Excel workbook to write
     *                 <p>
     *                 출력할 Excel {@link Workbook} 객체
     * @throws StreamOutputException if an error occurs while writing to the response
     *                               <p>
     *                               응답 스트림에 쓰는 도중 오류가 발생하면 {@link StreamOutputException}이 발생합니다.
     */
    @Override
    public void write(Workbook workbook) {
        try {
            String encodedName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
            response.setContentType(MIME_TYPE);
            response.setHeader(HEADER_NAME, String.format(CONTENT_DISPOSITION_FORMAT, encodedName));

            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            throw new StreamOutputException(e);
        }
    }
}