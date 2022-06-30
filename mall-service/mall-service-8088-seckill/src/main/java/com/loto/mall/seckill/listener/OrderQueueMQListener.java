package com.loto.mall.seckill.listener;

import com.alibaba.fastjson.JSON;
import com.loto.mall.seckill.service.SecKillOrderService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-30 15:36<p>
 * PageName：OrderQueueMQListener.java<p>
 * Function：
 */


@RocketMQMessageListener(
        topic = "order-queue",
        consumerGroup = "orderqueue-consumer",
        selectorExpression = "*"
)
@Component
public class OrderQueueMQListener implements RocketMQListener {
    @Autowired
    private SecKillOrderService secKillOrderService;

    /**
     * 消息监听
     *
     * @param message
     */
    @Override
    public void onMessage(Object message) {
        int count = secKillOrderService.add(JSON.parseObject(message.toString(), Map.class));

        System.out.println("-----------" + message);
        System.out.println("-----------" + count);
        // 根据count判断抢单是否成功
        // 如果成功了，则将数据推送到消息平台 -> 和客户端进行通信的消息平台 -> Netty + Weboscket
    }
}
