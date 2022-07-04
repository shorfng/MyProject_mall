package com.loto.mall.gateway.limit;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-07-04 9:06<p>
 * PageName：IpKeyResolver.java<p>
 * Function：
 */

public class IpKeyResolver implements KeyResolver {
    /**
     * 将IP作为限流标识
     *
     * @param exchange
     * @return
     */
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        // 获取客户端IP
        return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }
}
