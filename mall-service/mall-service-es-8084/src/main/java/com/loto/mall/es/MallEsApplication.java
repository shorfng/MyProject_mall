package com.loto.mall.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-17 20:02<p>
 * PageName：com.loto.mall.es.MallEsApplication.java<p>
 * Function：
 */

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableElasticsearchRepositories(basePackages = "com.loto.mall.es.mapper")
@EnableFeignClients
public class MallEsApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallEsApplication.class, args);
    }
}
