package com.loto.mall;

import com.github.wxpay.sdk.WXPay;
import com.loto.mall.pay.config.WeiXinPayConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-22 18:58<p>
 * PageName：MallPayApplication.java<p>
 * Function：
 */

@SpringBootApplication
@MapperScan(basePackages = {"com.loto.mall.pay.mapper"})
public class MallPayApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallPayApplication.class, args);
    }

    /**
     * 微信支付 SDK 对象
     * @param weiXinPayConfig
     * @return
     * @throws Exception
     */
    @Bean
    public WXPay wxPay(WeiXinPayConfig weiXinPayConfig) throws Exception {
        return new WXPay(weiXinPayConfig);
    }
}
