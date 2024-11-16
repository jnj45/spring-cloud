package com.example.orderservice.dto;

import lombok.Data;

import java.io.Serializable;

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
public class OrderDto implements Serializable {

    private String orderId;
    private String userId;
    private String productId;

    private Integer unitPrice;
    private Integer qty;
    private Integer totalPrice;
}
