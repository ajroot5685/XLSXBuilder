package com.github.ajroot5685.example.auto.servlet;

import jakarta.servlet.http.HttpServletResponse;

//@RestController
//@RequestMapping("/api/excel")
//@RequiredArgsConstructor
// or Servlet
public class ServletController {

    private final ServletOutputExampleService service = new ServletOutputExampleService();

    //    @GetMapping("")
    public void example(HttpServletResponse response) {
        service.fileExample(response);
    }
}
