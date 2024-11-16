package com.example.orderservice.dto;

import lombok.Data;

import java.util.Date;

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
public class RequestOrder {

    private String productId;

    private Integer unitPrice;
    private Integer qty;
}
