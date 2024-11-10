package com.example.userservice.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * << 개정이력 >>
 * - 2024-11-10 [ecbank-lyj] 최초 생성
 * </pre>
 *
 * @author ecbank-lyj
 * @version 1.0
 * @since 1.0
 */
@Component
@Data
public class Greeting {

    @Value("${greeting.message}")
    private String message;
}
