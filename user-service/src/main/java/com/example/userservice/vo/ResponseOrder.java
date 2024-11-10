package com.example.userservice.vo;

import lombok.Data;

import java.util.Date;

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
public class ResponseOrder {

    private String orderId;

    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private Date createAt;
}
