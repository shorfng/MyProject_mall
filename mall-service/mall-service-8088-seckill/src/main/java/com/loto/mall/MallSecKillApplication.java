package com.loto.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 14:32<p>
 * PageName：MallSecKillApplication.java<p>
 * Function：
 */

@SpringBootApplication
@MapperScan(basePackages = "com.loto.mall.seckill.mapper")
@EnableFeignClients(basePackages = {"com.loto.mall.api.seckill.feign"})
public class MallSecKillApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallSecKillApplication.class, args);
    }
}
