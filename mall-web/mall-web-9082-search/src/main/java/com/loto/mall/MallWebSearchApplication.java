package com.loto.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-19 19:16<p>
 * PageName：MallWebSearchApplication.java<p>
 * Function：
 */

@SpringBootApplication
@EnableFeignClients(basePackages = "com.loto.mall.api.search.feign")
public class MallWebSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallWebSearchApplication.class, args);
    }
}
