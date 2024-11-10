package com.example.userservice.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;
    @Column(nullable = false, length = 30)
    private String name;
    @Column(nullable = false, unique = true)
    private String userId;
    @Column(nullable = false, unique = false)
    private String encryptedPwd;

}
