package com.example.userservice.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * <pre>
 * << 개정이력 >>
 * - 2024-11-16 [ecbank-lyj] 최초 생성
 * </pre>
 *
 * @author ecbank-lyj
 * @version 1.0
 * @since 1.0
 */
@Data
public class RequestLogin {

    @NotNull(message = "userId cannot be null")
    private String userId;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password not be less then 8 characters")
    private String password;
}
