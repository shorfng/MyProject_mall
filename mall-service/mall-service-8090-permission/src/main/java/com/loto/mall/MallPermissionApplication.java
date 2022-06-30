package com.loto.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-30 22:08<p>
 * PageName：MallPermissionApplication.java<p>
 * Function：
 */

@SpringBootApplication
@MapperScan(basePackages = "com.loto.mall.permission.mapper")
public class MallPermissionApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallPermissionApplication.class, args);
    }
}
