## 📤 OutputWriter Overview

OutputWriter is the core interface responsible for writing the final Excel Workbook to an output destination.It allows you to decouple the Excel generation logic from the output target (file, stream, or servlet response), making the library flexible and reusable in different contexts.

---

## ✨ Purpose

- Abstracts the output mechanism (`Workbook.write(...)`)

- Allows consistent Excel generation regardless of output type

- Supports file-based, stream-based, and HTTP servlet-based output

---

## 🔧 Interface Definition

```java
public interface OutputWriter {
    void write(Workbook workbook);
}
```

---

## 🧩 Built-in Implementations

| Class | Target | Description |
| -- | -- | -- |
| FileOutputWriter | Path / file system | Writes .xlsx file to local disk |
| StreamOutputWriter | OutputStream | Writes to memory stream or external destination (S3, FTP, etc.) |
| ServletOutputWriter | HttpServletResponse | Writes directly to HTTP response for download in web applications |

---

## 📜 Usage Examples

```java
// File output
OutputWriter writer = WriterFactory.forFileOutput("excel.xlsx");

// Stream output
OutputWriter writer = WriterFactory.forStreamOutput(new ByteArrayOutputStream());

// Servlet output
OutputWriter writer = WriterFactory.forServletOutput(response, "excel.xlsx");
```

---

## 📝 Notes

- OutputWriter does not manage the lifecycle of streams (i.e., does not close them unless explicitly needed).

- It is designed to be lightweight and composable.

- You can implement your own OutputWriter (e.g., for cloud storage, zip output, etc.).