package com.loto.mall.gateway.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.loto.mall.api.permission.model.Permission;
import com.loto.mall.gateway.utils.IPUtils;
import com.loto.mall.util.security.JwtToken;
import com.loto.mall.util.security.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-30 19:37<p>
 * PageName：AuthorizationInterceptor.java<p>
 * Function：基于 JWT 令牌的权限校验
 */

@Component
public class AuthorizationInterceptor {
    //@Autowired
    //private RedissonClient redissonClient;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 判断 uri 是否为有效路径
     *
     * @param uri
     * @return
     */
    public Boolean isInvalid(String uri) {
        //RBloomFilter<String> uriBloomFilterArray = redissonClient.getBloomFilter("UriBloomFilterArray");
        //return uriBloomFilterArray.contains(uri);
        return true;
    }

    /**
     * 判断是否需要拦截校验
     */
    public Boolean isIntercept(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();

        // 获取uri（/cart/list）
        String uri = request.getURI().getPath();

        // 提交方式（GET/POST/* 等等）
        String method = request.getMethodValue();

        // 服务名字
        URI routerUri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        String serviceName = routerUri.getHost();

        // 从 Redis 缓存中进行匹配
        // 0:完全匹配
        String permissionsMatch0String = redisTemplate.boundHashOps("RolePermissionAll").get("PermissionMatch0").toString();
        List<Permission> permissionsMatch0 = JSON.parseObject(permissionsMatch0String, new TypeReference<>() {});

        if (permissionsMatch0 != null) {
            // TODO: 2022/6/30
        }

        // 1:通配符匹配
        Permission permission = match0(permissionsMatch0, uri, method, serviceName);

        // 如果 permission==null，则执行通配符匹配
        if (permission == null) {
            // 通配符匹配
            // TODO: 2022/6/30

            // 如果通配符匹配也为空，表明当前请求不需要进行权限校验
            // TODO: 2022/6/30

            // false 不需要拦截
            return false;
        }

        // true 需要拦截
        return true;
    }

    /**
     * 令牌校验
     */
    public Map<String, Object> tokenIntercept(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();

        // 校验其他地址
        String clientIp = IPUtils.getIp(request);

        // 获取令牌
        String token = request.getHeaders().getFirst("authorization");

        // 令牌校验
        Map<String, Object> resultMap = AuthorizationInterceptor.jwtVerify(token, clientIp);
        return resultMap;
    }

    /**
     * 权限校验
     */
    public Boolean rolePermission(ServerWebExchange exchange, Map<String, Object> token) {
        ServerHttpRequest request = exchange.getRequest();

        // 获取uri  /cart/list
        String uri = request.getURI().getPath();

        // 提交方式  GET/POST/*
        String method = request.getMethodValue();

        // 服务名字
        URI routerUri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        String serviceName = routerUri.getHost();

        // 匹配 -> 获取角色集合
        String[] roles = token.get("roles_id").toString().split(",");

        // 循环判断每个角色是否有权限
        Permission permission = null;
        for (String role : roles) {
            // 获取完全匹配权限集合
            String rolePermissionMapValue = redisTemplate.boundHashOps("RolePermissionMap").get("Role_0_" + role).toString();
            Set<Permission> rolePermissionMapValueSet = JSON.parseObject(rolePermissionMapValue, new TypeReference<>() {});

            if (rolePermissionMapValueSet == null) {
                continue;
            }

            // 循环判断
            permission = match0(new ArrayList<>(rolePermissionMapValueSet), uri, method, serviceName);
            if (permission != null) {
                break;
            }
        }

        // permission==null，通配符方式匹配
        if (permission == null) {
            // 通配符匹配
            // TODO: 2022/6/30
        }

        return permission != null;
    }

    /**
     * 匹配方法：完全匹配
     */
    public Permission match0(List<Permission> permissionsMatch0, String uri, String method, String serviceName) {
        for (Permission permission : permissionsMatch0) {
            String matchUrl = permission.getUrl();
            String matchMethod = permission.getMethod();

            if (matchUrl.equals(uri)) {
                // 提交方式和服务匹配
                if (matchMethod.equals(method) && serviceName.equals(permission.getServiceName())) {
                    return permission;
                }

                if ("*".equals(matchMethod) && serviceName.equals(permission.getServiceName())) {
                    return permission;
                }
            }
        }
        return null;
    }

    /**
     * 令牌解析
     *
     * @param token
     * @param clientIp
     * @return
     */
    public static Map<String, Object> jwtVerify(String token, String clientIp) {
        try {
            // 解析Token
            Map<String, Object> dataMap = JwtToken.parseToken(token);

            // 获取 Token 中 IP 的 MD5
            String ip = dataMap.get("ip").toString();

            // 比较：Token中IP的MD5值 和 用户的IP MD5值
            if (ip.equals(MD5.md5(clientIp))) {
                return dataMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
