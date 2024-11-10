package com.example.apigateway.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

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
@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter() {
        super(LoggingFilter.Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        //Pre Filter
//        return ((exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            ServerHttpResponse response = exchange.getResponse();
//
//            log.info("LoggingFilter baseMessage -> {}", config.getBaseMessage());
//
//            if (config.isPreLogger()) {
//                log.info("LoggingFilter Start: request id -> {}", request.getId());
//            }
//
//            //Post Filter
//            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//                if (config.isPostLogger()) {
//                    log.info("LoggingFilter Start: response code -> {}", response.getStatusCode());
//                }
//            }));
//        });

        //Post Filter

        return new OrderedGatewayFilter((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("LoggingFilter baseMessage -> {}", config.getBaseMessage());

            if (config.isPreLogger()) {
                log.info("Logging Pre filter : request id -> {}", request.getId());
            }

            //Post Filter
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostLogger()) {
                    log.info("Logging Post filter: response code -> {}", response.getStatusCode());
                }
            }));
        }, Ordered.LOWEST_PRECEDENCE);
    }

    @Data
    public static class Config{
        //Put the configuration properties
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
