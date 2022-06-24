package com.loto.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-24 15:55<p>
 * PageName：MallWebCartApplication.java<p>
 * Function：
 */

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.loto.mall.api.cart.feign"})
public class MallWebCartApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallWebCartApplication.class, args);
    }
}
