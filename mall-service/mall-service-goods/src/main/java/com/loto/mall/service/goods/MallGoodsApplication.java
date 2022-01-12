package com.loto.mall.service.goods;

import com.loto.config.StartConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.loto.mall.service.goods.mapper"})
public class MallGoodsApplication {
    public static void main(String[] args) {
        // 启动多个类
        SpringApplication.run(new Class[]{MallGoodsApplication.class, StartConfig.class}, args);
    }
}
