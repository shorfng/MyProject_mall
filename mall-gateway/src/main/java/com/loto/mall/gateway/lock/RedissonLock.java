package com.loto.mall.gateway.lock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-30 20:21<p>
 * PageName：RedissonLock.java<p>
 * Function：
 */

//@Configuration
public class RedissonLock {
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.password}")
    private String redisPassword;

    /***
     * 创建RedissonClient对象
     *      创建锁、解锁
     * @return
     */
    @Bean
    public RedissonClient redissonClient() {
        // 创建Config
        Config config = new Config();

        // 集群实现
        //config.useClusterServers()
        //        .setScanInterval(2000)
        //        .addNodeAddress(
        //                "redis://192.168.126.200:7001",
        //                "redis://192.168.126.200:7002",
        //                "redis://192.168.126.200:7003",
        //                "redis://192.168.126.200:7004",
        //                "redis://192.168.126.200:7005",
        //                "redis://192.168.126.200:7006");

        // 单机实现
        config.useSingleServer().setAddress("redis://" + redisHost + ":6379").setPassword(redisPassword);

        // 创建 RedissonClient
        return Redisson.create(config);
    }
}
