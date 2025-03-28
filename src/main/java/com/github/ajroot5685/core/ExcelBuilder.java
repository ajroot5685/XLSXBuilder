package com.github.ajroot5685.core;

import com.github.ajroot5685.constant.Default;
import com.github.ajroot5685.exception.ExcelGenerationException;
import com.github.ajroot5685.output.OutputWriter;
import java.io.IOException;
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
 * Utility class for generating Excel files using either manual or automatic column mapping.
 * <br>
 * 수동 또는 자동 컬럼 매핑을 사용해 Excel 파일을 생성하는 유틸리티 클래스입니다.
 */
public class ExcelBuilder {

    /**
     * Generates an Excel file using a manually defined column mapping.
     * <br>
     * The header names and value extractors are explicitly provided.
     * <br><br>
     * 수동으로 정의된 컬럼 매핑을 사용해 Excel 파일을 생성합니다.
     * <br>
     * 헤더 이름과 값 추출 함수가 명시적으로 전달됩니다.
     *
     * @param headersMap a mapping of header names to value extractor functions
     *                   <p>
     *                   헤더 이름과 DTO에서 값을 추출하는 함수의 매핑
     * @param bodyDto    the data to be written to the Excel file
     *                   <p>
     *                   Excel 파일에 쓸 본문 데이터 리스트
     * @param writer     the output writer to write the Excel file
     *                   <p>
     *                   Excel 파일을 출력할 {@link OutputWriter}
     * @param <T>        the DTO type
     */
    public static <T> void generateWithManualMapping(
            LinkedHashMap<String, Function<T, String>> headersMap,
            List<T> bodyDto,
            OutputWriter writer
    ) {
        generate(headersMap, bodyDto, writer);
    }

    /**
     * Generates an Excel file using reflection-based automatic column mapping.
     * <br>
     * Extracts headers and value functions automatically from the class using annotations.
     * <br><br>
     * 리플렉션 기반 자동 매핑을 사용해 Excel 파일을 생성합니다.
     * <br>
     * 클래스에 정의된 애노테이션을 통해 헤더와 추출 함수를 자동으로 추출합니다.
     *
     * @param clazz   the DTO class with annotated fields
     *                <p>
     *                애노테이션이 붙은 DTO 클래스
     * @param bodyDto the data to be written to the Excel file
     *                <p>
     *                Excel 파일에 쓸 본문 데이터 리스트
     * @param writer  the output writer to write the Excel file
     *                <p>
     *                Excel 파일을 출력할 {@link OutputWriter}
     * @param <T>     the DTO type
     */
    public static <T> void generateWithAutoMapping(
            Class<T> clazz,
            List<T> bodyDto,
            OutputWriter writer
    ) {
        LinkedHashMap<String, Function<T, String>> headersMap = ExcelAutoFieldExtractor.extract(clazz);
        generate(headersMap, bodyDto, writer);
    }

    /**
     * Internal method for generating Excel with provided header mapping and data.
     */
    private static <T> void generate(
            LinkedHashMap<String, Function<T, String>> headersMap,
            List<T> bodyDto,
            OutputWriter writer
    ) {
        List<Map<String, String>> excelResult = extractExcelDataFrom(headersMap, bodyDto);
        createExcel(headersMap.keySet(), excelResult, writer);
    }

    /**
     * Extracts Excel row data from DTO list using provided mapping.
     */
    private static <T> List<Map<String, String>> extractExcelDataFrom(
            LinkedHashMap<String, Function<T, String>> headersMap,
            List<T> bodyDto
    ) {
        return bodyDto.stream()
                .map(dto -> createRowFromDto(headersMap, dto))
                .toList();
    }

    /**
     * Converts a single DTO to a map of header → value.
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
     * Creates the Excel workbook, writes header and body rows, and writes to output.
     */
    private static void createExcel(
            Set<String> headerNames,
            List<Map<String, String>> bodyData,
            OutputWriter outputWriter
    ) {
        try (Workbook workbook = new SXSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(Default.SHEET_NAME);
            sheet.setDefaultColumnWidth(Default.CELL_WIDTH);

            createHeaderRow(sheet, headerNames);
            createBodyRows(sheet, bodyData);

            outputWriter.write(workbook);
        } catch (IOException e) {
            throw new ExcelGenerationException(e);
        }
    }

    /**
     * Creates the header row in the sheet.
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
     * Creates body rows in the sheet using the provided data.
     */
    private static void createBodyRows(Sheet sheet, List<Map<String, String>> bodyData) {
        int rowCount = 1;
        for (Map<String, String> rowData : bodyData) {
            Row row = sheet.createRow(rowCount++);
            int cellNum = 0;
            for (String data : rowData.values()) {
                Cell cell = row.createCell(cellNum++);
                cell.setCellValue(data);
            }
        }
    }
}