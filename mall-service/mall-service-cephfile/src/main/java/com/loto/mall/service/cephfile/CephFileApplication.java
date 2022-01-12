package com.loto.mall.service.cephfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CephFileApplication {
    public static void main(String[] args) {
        SpringApplication.run(CephFileApplication.class, args);
    }
}
