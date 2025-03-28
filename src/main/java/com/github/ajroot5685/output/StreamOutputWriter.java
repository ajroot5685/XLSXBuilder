package com.github.ajroot5685.output;

import com.github.ajroot5685.exception.StreamOutputException;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * {@link OutputWriter} implementation that writes Excel data to a given {@link OutputStream}.
 * <br>
 * Automatically flushes the stream after writing. Does not close the stream.
 * <br><br>
 * 지정된 {@link OutputStream}에 Excel 데이터를 쓰는 {@link OutputWriter} 구현체입니다.
 * <br>
 * 작성 후 스트림을 자동으로 flush 하지만, 스트림은 닫지 않습니다.
 */
public class StreamOutputWriter implements OutputWriter {

    private final OutputStream outputStream;

    /**
     * Constructs a new {@link StreamOutputWriter} with the given output stream.
     * <br><br>
     * 지정된 출력 스트림으로 {@link StreamOutputWriter}를 생성합니다.
     *
     * @param outputStream the output stream to write to
     *                     <p>
     *                     Excel 데이터를 출력할 {@link OutputStream}
     */
    public StreamOutputWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * Writes the given {@link Workbook} to the output stream.
     * <br>
     * Automatically calls {@code flush()} after writing. Does <strong>not</strong> close the stream. Caller must manage
     * the stream lifecycle.
     * <br><br>
     * 주어진 {@link Workbook}을 출력 스트림에 작성합니다.
     * <br>
     * 작성 후 {@code flush()}는 자동으로 수행되며, 스트림은 닫지 않으므로 호출자가 직접 생명 주기를 관리해야 합니다.
     *
     * @param workbook the Excel workbook to write
     *                 <p>
     *                 출력할 Excel {@link Workbook} 객체
     * @throws StreamOutputException if an I/O error occurs during writing
     *                               <p>
     *                               쓰기 도중 입출력 오류가 발생하면 {@link StreamOutputException}이 발생합니다.
     */
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