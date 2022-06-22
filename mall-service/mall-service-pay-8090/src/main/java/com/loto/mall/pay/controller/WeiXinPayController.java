package com.loto.mall.pay.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.loto.mall.api.pay.model.PayLog;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-22 19:05<p>
 * PageName：WeiXinPayController.java<p>
 * Function：
 */

@RestController
@RequestMapping(value = "/wxPay")
@Api(value = "WeiXinPayController", tags = "微信支付")
@CrossOrigin
public class WeiXinPayController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @ApiOperation(value = "支付结果回调")
    @GetMapping(value = "/result")
    public RespResult result() {
        // 创建日志对象
        PayLog payLog = new PayLog(IdWorker.getIdStr(), 1, "hello", "A", new Date());

        // 构建消息
        Message<String> message = MessageBuilder.withPayload(JSON.toJSONString(payLog)).build();

        // 发消息
        rocketMQTemplate.sendMessageInTransaction("rocket","log",message,null);

        return RespResult.ok();
    }

}
