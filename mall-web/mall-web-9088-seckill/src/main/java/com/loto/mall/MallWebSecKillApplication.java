package com.loto.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 16:49<p>
 * PageName：MallWebSecKillApplication.java<p>
 * Function：
 */

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.loto.mall.api.seckill.feign"})
public class MallWebSecKillApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallWebSecKillApplication.class, args);
    }
}
