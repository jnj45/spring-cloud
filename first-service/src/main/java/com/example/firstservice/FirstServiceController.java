package com.example.firstservice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * << 개정이력 >>
 * - 2024-11-09 [ecbank-lyj] 최초 생성
 * </pre>
 *
 * @author ecbank-lyj
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/first-service")
@Slf4j
@RequiredArgsConstructor
public class FirstServiceController {

    private final Environment environment;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to First Service!";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("first-request") String header) {
        log.info(header);
        return "Hello World in First Service!";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        log.info("server port={}", request.getServerPort());
        return String.format("Hi, Second Check! server port(env)=%s", environment.getProperty("local.server.port"));
    }
}
