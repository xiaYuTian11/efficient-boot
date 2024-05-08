package com.efficient.swagger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.efficient.swagger.service.SwaggerStartedEvent.DOC_SUFFIX;

/**
 * @author TMW
 * @since 2024/5/8 15:24
 */
@Controller
@RequestMapping("/doc.html")
public class DocController {

    @GetMapping
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect(DOC_SUFFIX);
    }
}
