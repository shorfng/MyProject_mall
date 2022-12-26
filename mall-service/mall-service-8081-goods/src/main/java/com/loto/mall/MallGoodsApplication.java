package com.loto.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(basePackages = {"com.loto.mall.service.goods.mapper"})
@EnableCaching // 开启缓存
public class MallGoodsApplication {
    public static void main(String[] args) {
        // 启动多个类
        //SpringApplication.run(new Class[]{MallGoodsApplication.class, StartConfig.class, RedisConfig.class}, args);
        SpringApplication.run(MallGoodsApplication.class, args);
    }
}
