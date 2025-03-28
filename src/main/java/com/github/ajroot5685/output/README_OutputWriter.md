## 📤 OutputWriter 살펴보기

OutputWriter는 출력 대상에 최종 Excel 워크북을 작성하는 핵심 인터페이스입니다. 이 인터페이스를 통해 Excel 생성 로직을 출력 대상(파일, 스트림 또는 서블릿 응답)에서 분리할 수 있어 라이브러리를 다양한 상황에서 유연하고 재사용할 수 있습니다.

---

## ✨ 목표

- 출력 메커니즘을 추상화합니다 (`Workbook.write(...)`)

- 출력 유형에 관계없이 일관된 엑셀 생성 가능

- 파일 기반, 스트림 기반, HTTP 서블릿 기반 출력 지원

---

## 🔧 인터페이스 정의

```java
public interface OutputWriter {
    void write(Workbook workbook);
}
```

---

## 🧩 구현체 목록

| Class | 타겟                  | 설명                                  |
| -- |---------------------|-------------------------------------|
| FileOutputWriter | Path / file system  | .xlsx 파일을 로컬 디스크에 씁니다               |
| StreamOutputWriter | OutputStream        | 메모리 스트림 또는 외부 대상(S3, FTP 등)에 쓰기     |
| ServletOutputWriter | HttpServletResponse | 웹 애플리케이션에서 다운로드하기 위해 HTTP 응답에 직접 쓰기 |

---

## 📜 사용 예시

```java
// File output
OutputWriter writer = WriterFactory.forFileOutput("excel.xlsx");

// Stream output
OutputWriter writer = WriterFactory.forStreamOutput(new ByteArrayOutputStream());

// Servlet output
OutputWriter writer = WriterFactory.forServletOutput(response, "excel.xlsx");
```

---

## 📝 참고

- OutputWriter는 스트림의 생명주기를 관리하지 않습니다(즉, 명시적으로 필요하지 않는 한 스트림을 닫지 않습니다).

- 이 라이브러리는 가볍고 조합 가능하도록 설계되었습니다.

- 클라우드 스토리지, zip 출력 등을 위해 자신만의 OutputWriter를 구현할 수 있습니다.