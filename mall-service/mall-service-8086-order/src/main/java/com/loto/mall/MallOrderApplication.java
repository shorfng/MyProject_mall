package com.loto.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-21 16:22<p>
 * PageName：MallOrderApplication.java<p>
 * Function：
 */

@SpringBootApplication
@MapperScan(basePackages = {"com.loto.mall.order.mapper"})
@EnableFeignClients(basePackages = {"com.loto.mall.api.goods.feign", "com.loto.mall.api.cart.feign"})
public class MallOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallOrderApplication.class, args);
    }
}
