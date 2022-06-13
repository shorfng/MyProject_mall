package com.loto.mall.service.cephfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)  // 因为不涉及数据库操作，所以排除数据库数据源加载操作
public class CephFileApplication {
    public static void main(String[] args) {
        SpringApplication.run(CephFileApplication.class, args);
    }
}
