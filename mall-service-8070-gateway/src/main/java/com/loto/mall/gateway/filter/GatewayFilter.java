package com.loto.mall.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-29 19:51<p>
 * PageName：GatewayFilter.java<p>
 * Function：
 */

@Configuration
public class GatewayFilter implements GlobalFilter, Ordered {

    /**
     * 执行拦截处理
     *
     * @param exchange the current server exchange
     * @param chain    provides a way to delegate to the next filter
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
