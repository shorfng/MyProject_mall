package com.loto.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-21 12:37<p>
 * PageName：MallUserApplication.java<p>
 * Function：
 */

@SpringBootApplication
@MapperScan(basePackages = {"com.loto.mall.user.mapper"})
public class MallUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallUserApplication.class, args);
    }
}
