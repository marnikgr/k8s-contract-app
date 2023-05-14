package com.uniwa.contract.gateway.filter;

import com.uniwa.contract.gateway.util.JwtUtils;
import com.uniwa.contract.gateway.util.RouteUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RefreshScope
@Slf4j
@AllArgsConstructor
public class AuthenticationFilter extends AbstractGatewayFilterFactory<Object> {

    private final JwtUtils jwtUtils;

    private final RouteUtils routeUtils;

    public GatewayFilter apply() {
        return this.apply(o -> {
        });
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            if (routeUtils.isSecured.test(request)) {
                if (!request.getHeaders().containsKey("Authorization")) {
                    log.error("Authorization header is missing in request");
                    return this.onError(exchange, HttpStatus.UNAUTHORIZED);
                }

                final String authHeader = request.getHeaders().getOrEmpty("Authorization").get(0);
                if (!jwtUtils.isValid(authHeader)) {
                    log.error("Authorization header is invalid");
                    return this.onError(exchange, HttpStatus.UNAUTHORIZED);
                }
            }
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange,HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

}
