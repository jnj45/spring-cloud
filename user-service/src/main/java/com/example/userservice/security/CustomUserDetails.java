package com.example.userservice.security;

import com.example.userservice.dto.UserDto;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * <pre>
 * << 개정이력 >>
 * - 2024-11-17 [ecbank-lyj] 최초 생성
 * </pre>
 *
 * @author ecbank-lyj
 * @version 1.0
 * @since 1.0
 */
public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

    private final UserDto userDto;

    public CustomUserDetails(String username, String password,
                             Collection<? extends GrantedAuthority> authorities,
                             UserDto userDto) {
        super(username, password, authorities);
        this.userDto = userDto;
    }

    public CustomUserDetails(String username, String password,
                             boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
                             Collection<? extends GrantedAuthority> authorities,
                             UserDto userDto) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userDto = userDto;
    }

    public UserDto getUserDto() {
        return userDto;
    }
}
