package com.loto.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-22 18:58<p>
 * PageName：MallPayApplication.java<p>
 * Function：
 */

@SpringBootApplication
@MapperScan(basePackages = {"com.loto.mall.pay.mapper"})
public class MallPayApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallPayApplication.class, args);
    }
}
