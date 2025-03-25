package com.github.ajroot5685.example;

import com.github.ajroot5685.core.ExcelBuilder;
import com.github.ajroot5685.output.WriterType;
import java.util.List;

//@RestController
//@RequestMapping("/api/excel")
//@RequiredArgsConstructor
public class ExampleController {

//    private final ExampleService exampleService;
//
//    public ExampleController(ExampleService exampleService) {
//        this.exampleService = exampleService;
//    }

    //    @GetMapping("")
//    public void example(HttpServletResponse response) {
//        exampleService.writeExampleExcel("sheet1", null, response);
//
//        String fileName = ExcelFileNameConstant.EXAMPLE;
//        ExcelDownloadUtil.setResponse(response, fileName);
//
//        // OutputStream으로 데이터 전송
//        // 프론트에서 이 방식으로 처리할 수 있는지 확인 필요
//        try (OutputStream outputStream = response.getOutputStream()) {
//            exampleService.writeExampleExcel(fileName, new Object(), outputStream);
//            outputStream.flush();
//        } catch (IOException e) {
//            throw new CustomException(ErrorCode.EXCEL_CREATE_FAIL);
//        }
//    }

    public void fileExample() {
        List<ExampleRes> excelDto = List.of(
                new ExampleRes("1", "예시"),
                new ExampleRes("2", "예시2")
        );

        ExcelBuilder.generate(ExampleRes.class, excelDto, "시트1", WriterType.FILE, "경로.xlsx",
                "엑셀1");
    }

    public static void main(String[] args) {
        new ExampleController().fileExample();
    }
}
