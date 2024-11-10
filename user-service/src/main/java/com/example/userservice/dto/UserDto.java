package com.example.userservice.dto;

import com.example.userservice.vo.ResponseOrder;
import lombok.Data;

import java.util.Date;
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
public class UserDto {

    private String email;
    private String pwd;
    private String name;
    private String userId;
    private Date createAt;

    private String encryptedPwd;

    private List<ResponseOrder> orders;
}
