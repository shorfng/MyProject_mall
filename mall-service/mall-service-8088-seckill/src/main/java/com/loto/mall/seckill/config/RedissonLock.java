package com.loto.mall.seckill.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-30 16:20<p>
 * PageName：RedissonLock.java<p>
 * Function：
 */

@Configuration
public class RedissonLock {
    /**
     * 用于创建锁、解锁
     * @return
     */
    @Bean
    public RedissonClient redissonClient(){
        // 创建Config
        Config config = new Config();

        // 集群实现
        //config.useClusterServers().setScanInterval(2000).addNodeAddress("xxxx");

        // 单机实现
        config.useSingleServer().setAddress("redis://192.168.126.200:6379");

        // 创建 RedissonClient
        return Redisson.create(config);
    }
}
