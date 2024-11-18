package com.example.apigateway.filter;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

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
@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    private final Environment env;

    @Override
    public GatewayFilter apply(Config config) {
        //Global Pre Filter
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "no authorization header", HttpStatus.UNAUTHORIZED);
            }

            String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            String jwt = authorization.replace("Bearer ", "");

            if (!isJwtValid(jwt)) {
                return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
            }

            //Global Post Filter
            return chain.filter(exchange);
        });
    }

    private boolean isJwtValid(String jwt) {
        boolean isValid = false;

        String secret = env.getProperty("token.secret");
        byte[] secretKeyBytes = Base64.getEncoder().encode(secret.getBytes());
        SecretKeySpec signingKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());

        String subject = null;

        try {
            JwtParser jwtParser = Jwts.parser().setSigningKey(signingKey).build();
            subject = jwtParser.parseClaimsJws(jwt).getBody().getSubject();

            if (subject != null && !subject.isEmpty()) {
                isValid = true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return isValid;
    }

    //Mono(단일값), Flux(다중값) --> Spring WebFlux
    private Mono<Void> onError(ServerWebExchange exchange, String noAuthorizationHeader, HttpStatus httpStatus) {
        exchange.getResponse().setStatusCode(httpStatus);

        log.error(noAuthorizationHeader);

        return exchange.getResponse().setComplete();
    }

    public static class Config {
    }


}
