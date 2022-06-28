package com.loto.mall.canal.job.dynamicjob;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-28 11:24<p>
 * PageName：DynamicConfig.java<p>
 * Function：
 */

@Configuration
public class DynamicConfig {
    @Value("${dynamiczk}")
    private String dynamiczk;
    @Value("${dynamicnamespace}")
    private String dynamicnamespace;

    /**
     * 指定当前注册地址信息
     */
    @Bean
    public ZookeeperConfiguration zookeeperConfiguration() {
        return new ZookeeperConfiguration(dynamiczk, dynamicnamespace);
    }

    /**
     * 向 Zookeeper 服务注册
     */
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter zookeeperRegistryCenter(ZookeeperConfiguration zookeeperConfiguration) {
        return new ZookeeperRegistryCenter(zookeeperConfiguration);
    }
}
