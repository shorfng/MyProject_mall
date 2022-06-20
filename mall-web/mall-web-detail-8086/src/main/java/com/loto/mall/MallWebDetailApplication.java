package com.loto.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-20 17:42<p>
 * PageName：MallWebDetailApplication.java<p>
 * Function：
 */

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.loto.mall.api.goods.feign"})
public class MallWebDetailApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallWebDetailApplication.class, args);
    }
}
