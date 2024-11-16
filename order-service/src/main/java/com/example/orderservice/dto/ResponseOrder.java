package com.example.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOrder {

    private String orderId;
    private String productId;

    private Integer unitPrice;
    private Integer qty;
    private Integer totalPrice;

    private Date createAt;
}
