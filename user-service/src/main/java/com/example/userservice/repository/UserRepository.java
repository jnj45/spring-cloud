package com.example.userservice.repository;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

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
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUserId(String userId);
}
