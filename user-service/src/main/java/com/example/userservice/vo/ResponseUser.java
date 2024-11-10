package com.example.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUser {

    private String email;
    private String name;
    private String userId;

    private List<ResponseOrder> orders;
}
