package com.loto.mall.gateway.interceptor;

import com.loto.mall.api.permission.model.Permission;
import com.loto.mall.gateway.utils.IPUtils;
import com.loto.mall.util.security.JwtToken;
import com.loto.mall.util.security.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.data.redis.core.RedisTemplate;
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
    private RedisTemplate redisTemplate;

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
     * 是否需要拦截校验
     */
    public Boolean isIntercept(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();

        // 获取uri  /cart/list
        String uri = request.getURI().getPath();

        // 提交方式  GET/POST/*
        String method = request.getMethodValue();

        // 服务名字
        URI routerUri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        String servicename = routerUri.getHost();

        // 从Redis缓存中进行匹配
        // 0:完全匹配
        List<Permission> permissionsMatch0 = (List<Permission>) redisTemplate.boundHashOps("RolePermissionAll").get("PermissionMatch0");
        if (permissionsMatch0 != null) {

        }

        // 1:通配符匹配
        Permission permission = match0(permissionsMatch0, uri, method, servicename);

        // 如果permission==null，则执行通配符匹配
        if (permission == null) {
            // 通配符匹配

            // 如果通配符匹配也为空，表明当前请求不需要进行权限校验
            return false;
        }

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
        String servicename = routerUri.getHost();

        // 匹配->获取角色集合
        String[] roles = token.get("roles").toString().split(",");

        // 循环判断每个角色是否有权限
        Permission permission = null;
        for (String role : roles) {
            // 获取完全匹配权限集合
            Set<Permission> permissions = (Set<Permission>) redisTemplate.boundHashOps("RolePermissionMap").get("Role_0_" + role);

            if (permissions == null) {
                continue;
            }

            // 循环判断
            permission = match0(new ArrayList<Permission>(permissions), uri, method, servicename);
            if (permission != null) {
                break;
            }
        }

        // permission==null，通配符方式匹配
        if (permission == null) {
            // 通配符匹配
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
