# 📄 SimpleExcelBuilder

A lightweight Java library for generating simple `.xlsx` Excel files with annotation-based or manual column mapping.

---

## ✨ Features

- ✅ Annotation-based automatic column mapping (`@ExcelColumn`)
- ✅ Manual header and mapping support
- ✅ Output to file, stream, or servlet response
- ✅ Apache POI-based (uses SXSSFWorkbook)

---

## 📦 Installation (via JitPack)

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.your-username:simple-xlsx:{version}'
}
```

---

## 🧩 Annotation-based Example(Recommend)

> Automatically constructs mapping data from DTO and generates Excel using Reflection.

First, define the Excel DTO to be delivered.

```java
public class AutoExampleDto {
    @XLSXColumn(header = "No")
    private final String no;

    @XLSXColumn(header = "name")
    private final String name;

    public AutoExampleDto(String no, String name) {
        this.no = no;
        this.name = name;
    }
}

```

To generate Excel, we need Excel DTO list and OutputWriter object.

```java
public class FileOutputExample {
    // Must end with a .xlsx extension.
    // If not, it sticks automatically.
    private final String outputPath = "path/fileName.xlsx";

    public void fileExample() {
        List<AutoExampleDto> excelDto = List.of(
                new AutoExampleDto("1", "example"),
                new AutoExampleDto("2", "example2")
        );

        // Writer generation is required.
        OutputWriter writer = WriterFactory.forFileOutput(outputPath);
        ExcelBuilder.generateWithAutoMapping(AutoExampleDto.class, excelDto, writer);
    }
}
```

---

## 🍃 Annotation-based Example with Servlet/Spring

> This example demonstrates how to use `@ExcelColumn` for automatic Excel column mapping in a Spring-based web application.  
>
> The generated Excel file is streamed directly to the client via `HttpServletResponse`.

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
                new AutoExampleDto("1", "example"),
                new AutoExampleDto("2", "example2")
        );

        // Writer generation is required.
        OutputWriter writer = WriterFactory.forServletOutput(response, fileName);
        ExcelBuilder.generateWithAutoMapping(AutoExampleDto.class, excelDto, writer);
    }
}
```

---

## 📝 Manual Mapping Example

> Since we don't use reflections internally, we need to deliver the mapping information object so that we can get the
> value of the DTO.
>
> For users who should not use reflection.

Declare getter because annotation cannot be written.

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

To generate Excel, we need Excel DTO list and OutputWriter object.

```java
public class FileOutputExample {
    // Must end with a .xlsx extension.
    // If not, it sticks automatically.
    private final String outputPath = "path/fileName.xlsx";

    public void fileExample() {
        List<ManualExampleDto> excelDto = List.of(
                new ManualExampleDto("1", "example"),
                new ManualExampleDto("2", "example2")
        );

        // Writer generation is required.
        OutputWriter writer = WriterFactory.forFileOutput(outputPath);
        ExcelBuilder.generateWithManualMapping(getHeaderAndMappingFunction(), excelDto, writer);
    }

    private LinkedHashMap<String, Function<ManualExampleDto, String>> getHeaderAndMappingFunction() {
        LinkedHashMap<String, Function<ManualExampleDto, String>> result = new LinkedHashMap<>();
        result.put("No", ManualExampleDto::getNo);
        result.put("Name", ManualExampleDto::getName);
        return result;
    }
}
```

---

## 👀 For more information..

- [OutputWriter](/src/main/java/com/github/ajroot5685/output/README_OutputWriter_EN.md)
- [Examples](/src/main/java/com/github/ajroot5685/example)

> And please refer to the JavaDoc document.