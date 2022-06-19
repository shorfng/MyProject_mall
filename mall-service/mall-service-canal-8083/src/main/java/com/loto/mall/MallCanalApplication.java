package com.loto.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-17 0:58<p>
 * PageName：MallCanalApplication.java<p>
 * Function：
 */

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients(basePackages = {"com.loto.mall.api.goods.feign", "com.loto.mall.api.search.feign"})
public class MallCanalApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallCanalApplication.class, args);
    }
}
