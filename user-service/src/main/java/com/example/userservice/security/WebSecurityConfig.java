package com.example.userservice.security;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final Environment environment;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(authorizeRequests
                -> authorizeRequests.requestMatchers("/actuator/**").permitAll());
        http.authorizeHttpRequests(authorize ->
                    authorize.anyRequest().permitAll() // 모든 요청 허용
            );

//        http.authorizeHttpRequests(authorizeRequests ->
//                authorizeRequests.anyRequest().authenticated());

        http.addFilter(getAuthenticationFilter());

        //iframe에서 페이지가 로드될 수 있도록. h2 console을 위해.
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return http.build();
    }

    private Filter getAuthenticationFilter() throws Exception {
        CustomUsernamePasswordAuthenticationFilter authenticationFilter = new CustomUsernamePasswordAuthenticationFilter(environment);
        authenticationFilter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
//        authenticationFilter.setAuthenticationManager(authenticationManager());
        return authenticationFilter;
    }

//    @Bean
//    public AuthenticationManager authenticationManager() throws Exception {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return new ProviderManager(authProvider);
//    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
