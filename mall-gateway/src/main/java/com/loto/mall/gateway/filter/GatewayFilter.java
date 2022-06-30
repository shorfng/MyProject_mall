package com.loto.mall.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.loto.mall.gateway.interceptor.AuthorizationInterceptor;
import com.loto.mall.gateway.queue.HotQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-29 19:51<p>
 * PageName：GatewayFilter.java<p>
 * Function：
 */

@Configuration
public class GatewayFilter implements GlobalFilter, Ordered {
    @Autowired
    private HotQueue hotQueue;

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    /**
     * 执行拦截处理
     *
     * @param exchange the current server exchange
     * @param chain    provides a way to delegate to the next filter
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 获取用户请求的 uri
        String uri = request.getURI().getPath();

        // 过滤uri是否有效
        if (!authorizationInterceptor.isInvalid(uri)) {
            endProcess(exchange, 404, "url bad");
            return chain.filter(exchange);
        }

        // 判断是否需要拦截
        //if (!authorizationInterceptor.isIntercept(exchange)) {
        //    return chain.filter(exchange);
        //}

        // 令牌校验
        Map<String, Object> resultMap = authorizationInterceptor.tokenIntercept(exchange);
        //if (resultMap == null || !authorizationInterceptor.rolePermission(exchange, resultMap)) {
        if (resultMap == null) {
            // 令牌校验失败 / 没有权限
            endProcess(exchange, 401, "Access denied");
            return chain.filter(exchange);
        }

        // 秒杀过滤
        if (uri.equals("/seckill/order")) {
            secKillFilter(exchange, request, resultMap.get("username").toString());
            return chain.filter(exchange);
        }

        // NOT_HOT 直接由后端服务处理
        return chain.filter(exchange);
    }

    /**
     * 结束程序
     *
     * @param exchange
     * @param code
     * @param message
     */
    public void endProcess(ServerWebExchange exchange, Integer code, String message) {
        // 响应状态码200
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", code);
        resultMap.put("message", message);

        exchange.getResponse().setStatusCode(HttpStatus.OK);
        exchange.getResponse().setComplete();
        exchange.getResponse().getHeaders().add("message", JSON.toJSONString(resultMap));
    }

    /**
     * 秒杀过滤
     *
     * @param exchange
     * @param request
     * @param username
     * @return
     */
    private void secKillFilter(ServerWebExchange exchange, ServerHttpRequest request, String username) {
        // 商品ID
        String id = request.getQueryParams().getFirst("id");

        // 数量
        Integer num = Integer.valueOf(request.getQueryParams().getFirst("num"));

        // 排队结果
        int result = hotQueue.hotToQueue(username, id, num);

        // QUEUE_ING、HAS_QUEUE
        if (result == HotQueue.QUEUE_ING || result == HotQueue.HAS_QUEUE) {
            endProcess(exchange, result, "hot");
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
