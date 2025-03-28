package com.github.ajroot5685.output;

import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * Factory class for creating various types of {@link OutputWriter} instances.
 * <br>
 * 다양한 종류의 {@link OutputWriter} 인스턴스를 생성하는 팩토리 클래스입니다.
 */
public class WriterFactory {

    /**
     * Returns a {@link FileOutputWriter} that writes to the specified file path.
     * <br>
     * Appends the ".xlsx" extension automatically if it is missing.
     * <br><br>
     * 지정된 파일 경로에 쓰는 {@link FileOutputWriter}를 반환합니다.
     * <br>
     * 확장자가 없으면 ".xlsx"가 자동으로 붙습니다.
     *
     * @param path the output file path (e.g., "output/report.xlsx")
     *             <p>
     *             출력 파일 경로 (예: "output/report.xlsx")
     * @return a {@link FileOutputWriter} for file output
     * <p>
     * 파일 출력용 {@link FileOutputWriter}
     */
    public static OutputWriter forFileOutput(String path) {
        return new FileOutputWriter(path);
    }

    /**
     * Returns a {@link StreamOutputWriter} that writes to the given {@link OutputStream}.
     * <br>
     * Automatically flushes the stream after writing. Does not close the stream.
     * <br><br>
     * 주어진 {@link OutputStream}에 쓰는 {@link StreamOutputWriter}를 반환합니다.
     * <br>
     * write() 후 자동으로 flush()를 수행하며, 스트림은 닫지 않습니다.
     *
     * @param stream the output stream to write to
     *               <p>
     *               출력 대상 {@link OutputStream}
     * @return a {@link StreamOutputWriter} for stream output
     * <p>
     * 스트림 출력용 {@link StreamOutputWriter}
     */
    public static OutputWriter forStreamOutput(OutputStream stream) {
        return new StreamOutputWriter(stream);
    }

    /**
     * Returns a {@link ServletOutputWriter} that writes Excel data to an HTTP response.
     * <br>
     * Does not call flush() after writing. The caller is responsible for response handling.
     * <br><br>
     * HTTP 응답에 Excel 데이터를 쓰는 {@link ServletOutputWriter}를 반환합니다.
     * <br>
     * write() 후 flush()는 수행하지 않으며, 응답 처리는 호출자가 직접 해야 합니다.
     *
     * @param response the HTTP servlet response to write to
     *                 <p>
     *                 Excel 데이터를 쓸 HTTP 응답 객체
     * @param fileName the name of the file to be downloaded (".xlsx" is appended if missing)
     *                 <p>
     *                 다운로드 파일 이름 (확장자가 없으면 ".xlsx"가 자동으로 붙습니다)
     * @return a {@link ServletOutputWriter} for servlet response output
     * <p>
     * HTTP 응답 출력용 {@link ServletOutputWriter}
     */
    public static OutputWriter forServletOutput(HttpServletResponse response, String fileName) {
        return new ServletOutputWriter(response, fileName);
    }
}