package com.example.userservice.security;

import com.example.userservice.vo.RequestLogin;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

/**
 * <pre>
 * << 개정이력 >>
 * - 2024-11-16 [ecbank-lyj] 최초 생성
 * </pre>
 *
 *
 * @author ecbank-lyj
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Environment env;

    public CustomUsernamePasswordAuthenticationFilter(Environment env) {
        this.env = env;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            RequestLogin requestLogin = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);

            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(requestLogin.getUserId(), requestLogin.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.debug("Successful authentication");
        log.debug("authResult: {}", authResult);
        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();
        log.debug("userId: {}", userDetails.getUsername());

        String secret = env.getProperty("token.secret");
        byte[] secretKeyBytes = Base64.getEncoder().encode(secret.getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

        Instant now = Instant.now();
        String token = Jwts.builder()
                .subject(userDetails.getUsername())
                .expiration(Date.from(now.plusMillis(Long.parseLong(env.getProperty("token.expiration")))))
                .issuedAt(Date.from(now))
                .signWith(secretKey)
                .compact();

        response.addHeader("token", token);
        response.addHeader("userId", userDetails.getUsername());
    }
}
