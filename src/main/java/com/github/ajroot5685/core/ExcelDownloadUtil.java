package com.github.ajroot5685.core;

import com.github.ajroot5685.component.Default;
import com.github.ajroot5685.exception.ExcelGenerationException;
import com.github.ajroot5685.output.OutputWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * 목록조회 페이지에서 엑셀로 다운로드
 */
public class ExcelDownloadUtil {
    private OutputWriter _outputWriter;

    /**
     * 엑셀 다운로드
     * <p>
     * LinkedHashMap의 특성으로 인해 데이터를 처리할 때에도 순서가 유지됨
     *
     * @param headersMap   제목 행과 값 추출 함수
     * @param responseDtos 값
     * @param title        엑셀 제목
     * @param <T>          1차 전처리된 엑셀 데이터 타입
     */
    public static <T> void generate(
            LinkedHashMap<String, Function<T, String>> headersMap,
            List<T> responseDtos,
            String title,
            OutputStream outputStream
    ) {
        List<Map<String, String>> excelResult = extractExcelDataFrom(headersMap, responseDtos);

        ExcelCreateData excelCreateData = new ExcelCreateData(headersMap.keySet(), excelResult, title);

        createExcel(excelCreateData, outputStream);
    }

    /**
     * 엑셀 body 행 데이터 2차 전처리
     */
    private static <T> List<Map<String, String>> extractExcelDataFrom(
            LinkedHashMap<String, Function<T, String>> headersMap,
            List<T> responseDtos
    ) {
        return responseDtos.stream()
                .map(dto -> createRowFromDto(headersMap, dto))
                .toList();
    }

    /**
     * 엑셀 body 행 데이터 2차 전처리 - 행 하나 처리
     */
    private static <T> Map<String, String> createRowFromDto(
            LinkedHashMap<String, Function<T, String>> headersMap,
            T dto
    ) {
        Map<String, String> row = new LinkedHashMap<>();
        headersMap.forEach((header, extractor) -> row.put(header, extractor.apply(dto)));
        return row;
    }

    /**
     * 엑셀 생성
     *
     * @param excelCreateData 전처리된 엑셀 데이터
     * @param outputStream    outputStream에 직접 쓰기, 메모리 적재 과정이 없으므로 성능 개선됨
     */
    private static void createExcel(ExcelCreateData excelCreateData, OutputStream outputStream) {
        List<Map<String, String>> excelData = excelCreateData.getExcelData();
        Set<String> headerNames = excelCreateData.getHeaders();
        String excelName = excelCreateData.getTitle();

        try (Workbook workbook = new SXSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(excelName);
            sheet.setDefaultColumnWidth(Default.CELL_WIDTH);

            createHeaderRow(sheet, headerNames);
            createBodyRows(sheet, excelData);

            workbook.write(outputStream);
        } catch (IOException e) {
            throw new ExcelGenerationException(e);
        }
    }

    /**
     * 제목 행 삽입
     */
    private static void createHeaderRow(Sheet sheet, Set<String> headerNames) {
        Row headerRow = sheet.createRow(0);
        int i = 0;
        for (String headerName : headerNames) {
            Cell cell = headerRow.createCell(i++);
            cell.setCellValue(headerName);
        }
    }

    /**
     * body 행 삽입
     */
    private static void createBodyRows(Sheet sheet, List<Map<String, String>> excelData) {
        int rowCount = 1; // 헤더가 0번 행에 추가됨
        for (Map<String, String> rowData : excelData) {
            Row row = sheet.createRow(rowCount++);
            int cellNum = 0;

            for (String data : rowData.values()) {
                Cell cell = row.createCell(cellNum++);
                cell.setCellValue(data);
            }
        }
    }
}