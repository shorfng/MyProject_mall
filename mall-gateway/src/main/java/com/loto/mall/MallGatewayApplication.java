package com.loto.mall;

import com.loto.mall.gateway.limit.IpKeyResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-29 19:49<p>
 * PageName：MallGatewayApplication.java<p>
 * Function：
 */

@SpringBootApplication
public class MallGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallGatewayApplication.class, args);
    }

    /**
     * IP 限流
     * @return
     */
    @Bean("ipKeyResolver")
    public KeyResolver ipKeyResolver(){
        return new IpKeyResolver();
    }
}
