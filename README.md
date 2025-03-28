# 📄 SimpleExcelBuilder

애노테이션 기반 자동 매핑 또는 수동 열 매핑을 사용하여 간단한 '.xlsx' Excel 파일을 생성하기 위한 경량 Java 라이브러리입니다.

[README For EN User](/README_EN.md)

---

## ✨ 기능

- ✅ 애노테이션 기반 자동 열 매핑 (`@ExcelColumn`)
- ✅ 수동 헤더 및 매핑 지원
- ✅ 파일, 스트림 또는 서블릿 응답으로 출력
- ✅ Apache POI 기반(SXSSFworkbook 사용)

---

## 📦 설치 (JitPack)

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.your-username:simple-xlsx:{version}'
}
```

---

## 🧩 애노테이션 기반 예시(추천)

> DTO에서 매핑 데이터를 자동으로 구성하고 리플렉션을 사용하여 엑셀을 생성합니다.

먼저, 전달할 엑셀 DTO를 정의합니다.

```java
public class AutoExampleDto {
    @XLSXColumn(header = "번호")
    private final String no;

    @XLSXColumn(header = "이름")
    private final String name;

    public AutoExampleDto(String no, String name) {
        this.no = no;
        this.name = name;
    }
}

```

엑셀을 생성하려면 엑셀 DTO 리스트와 OutputWriter 객체가 필요합니다.

```java
public class FileOutputExample {
    // Must end with a .xlsx extension.
    // If not, it sticks automatically.
    private final String outputPath = "path/fileName.xlsx";

    public void fileExample() {
        List<AutoExampleDto> excelDto = List.of(
                new AutoExampleDto("1", "예시"),
                new AutoExampleDto("2", "예시2")
        );

        // Writer generation is required.
        OutputWriter writer = WriterFactory.forFileOutput(outputPath);
        ExcelBuilder.generateWithAutoMapping(AutoExampleDto.class, excelDto, writer);
    }
}
```

---

## 🍃 서블릿/스프링에서의 애노테이션 기반 예시

> 이 예시는 스프링 기반 웹 애플리케이션에서 자동 엑셀 열 매핑을 위해 `@ExcelColumn`을 사용하는 방법을 보여줍니다. 
> 
> 생성된 엑셀 파일은 `HttpServletResponse`를 통해 클라이언트로 직접 스트리밍됩니다.

```java
@RestController
@RequestMapping("/api/excel")
@RequiredArgsConstructor
public class ExcelController {

    private final ExcelService excelService;

    @GetMapping("")
    public void example(HttpServletResponse response) {
        excelService.excelService(response);
    }
}
```

```java
public class ServletOutputExampleService {
    // Must end with a .xlsx extension.
    // If not, it sticks automatically.
    private final String fileName = "fileName.xlsx";

    public void servletExample(HttpServletResponse response) {
        List<AutoExampleDto> excelDto = List.of(
                new AutoExampleDto("1", "예시"),
                new AutoExampleDto("2", "예시2")
        );

        // Writer generation is required.
        OutputWriter writer = WriterFactory.forServletOutput(response, fileName);
        ExcelBuilder.generateWithAutoMapping(AutoExampleDto.class, excelDto, writer);
    }
}
```

---

## 📝 Manual Mapping Example

> 내부적으로 리플렉션을 사용하지 않기 때문에, DTO 값을 얻기 위해 매핑 정보 객체를 전달해야 합니다.
>
> 리플렉션을 사용하면 안되는 사용자들을 위해 제공합니다.

애노테이션을 사용할 수 없으므로 getter를 선언합니다.

```java
public class ManualExampleDto {
    private final String no;
    private final String name;

    public ManualExampleDto(String no, String name) {
        this.no = no;
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public String getName() {
        return name;
    }
}

```

엑셀을 생성하려면 엑셀 DTO 목록과 OutputWriter 객체가 필요합니다.

```java
public class FileOutputExample {
    // Must end with a .xlsx extension.
    // If not, it sticks automatically.
    private final String outputPath = "path/fileName.xlsx";

    public void fileExample() {
        List<ManualExampleDto> excelDto = List.of(
                new ManualExampleDto("1", "예시"),
                new ManualExampleDto("2", "예시2")
        );

        // Writer generation is required.
        OutputWriter writer = WriterFactory.forFileOutput(outputPath);
        ExcelBuilder.generateWithManualMapping(getHeaderAndMappingFunction(), excelDto, writer);
    }

    private LinkedHashMap<String, Function<ManualExampleDto, String>> getHeaderAndMappingFunction() {
        LinkedHashMap<String, Function<ManualExampleDto, String>> result = new LinkedHashMap<>();
        result.put("번호", ManualExampleDto::getNo);
        result.put("이름", ManualExampleDto::getName);
        return result;
    }
}
```

---

## 👀 더 많은 정보를 원한다면..

- [OutputWriter](/src/main/java/com/github/ajroot5685/output/README_OutputWriter.md)
- [Examples](/src/main/java/com/github/ajroot5685/example)

> 그리고 자바독 문서를 참고해주세요.