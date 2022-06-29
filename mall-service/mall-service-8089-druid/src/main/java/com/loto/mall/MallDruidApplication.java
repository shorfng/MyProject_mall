package com.loto.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-29 15:25<p>
 * PageName：MallDruidApplication.java<p>
 * Function：
 */

@SpringBootApplication
@MapperScan(basePackages = "com.loto.mall.druid.mapper")
public class MallDruidApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallDruidApplication.class, args);
    }
}
