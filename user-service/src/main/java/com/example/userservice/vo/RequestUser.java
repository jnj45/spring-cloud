package com.example.userservice.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
@Data
public class RequestUser {

    @NotNull(message = "User id cannot be null")
    private String userId;

    @NotNull(message = "Email cannot be null")
    private String email;

    @NotNull(message = "pwd cannot be null")
    private String pwd;

    @NotNull(message = "name cannot be null")
    private String name;
}
