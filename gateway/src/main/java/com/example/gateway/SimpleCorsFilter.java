package com.example.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
public class SimpleCorsFilter {
    private static final String ALLOWED_HEADERS =
            "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN, Upgrade, Referrer";
    private static final String ALLOWED_METHODS = "GET, PUT, POST, DELETE, OPTIONS";
    private static final String ALLOWED_ORIGIN = "http://localhost:4200";
    private static final String MAX_AGE = "3600";

    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            System.out.println("Fui chamado");
            ServerHttpRequest request = ctx.getRequest();
            this.showRequestHeaders(request);
            this.showResponseHeaders(ctx.getResponse());
            if (CorsUtils.isCorsRequest(request)) {
                ServerHttpResponse response = ctx.getResponse();
                HttpHeaders headers = response.getHeaders();
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, ALLOWED_ORIGIN);
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, ALLOWED_METHODS);
                headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADERS);
                // headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }
            return chain.filter(ctx);
        };
    }

    private void showRequestHeaders(ServerHttpRequest request) {
        System.out.println("------PRINTING REQUEST HEADERS-----");
        request.getHeaders().forEach((k, v) -> System.out.println(k + " => " + v));
        System.out.println("------END OF PRINTING REQUEST HEADERS-----");
    }

    private void showResponseHeaders(ServerHttpResponse response) {
        System.out.println("------PRINTING RESPONSE HEADERS-----");
        response.getHeaders().forEach((k, v) -> System.out.println(k + " => " + v));
        System.out.println("------END OF PRINTING RESPONSE HEADERS-----");
    }
}
