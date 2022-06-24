package com.loto.mall.pay.config;

import com.loto.mall.util.security.Signature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-24 13:35<p>
 * PageName：SecurityConfig.java<p>
 * Function：
 */

@Configuration
public class SecurityConfig {
    @Value("${payConfig.aes.skey}")
    private String skey;

    @Value("${payConfig.aes.salt}")
    private String salt;

    /**
     * 加密解密工具类
     *
     * @return
     */
    @Bean
    public Signature signature() {
        return new Signature(skey, salt);
    }
}
