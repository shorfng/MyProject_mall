package com.loto.mall.order.listener;

import com.alibaba.fastjson.JSON;
import com.loto.mall.api.pay.model.PayLog;
import com.loto.mall.order.service.IOrderService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-22 19:28<p>
 * PageName：OrderResultMQListener.java<p>
 * Function：
 */

@Component
@RocketMQMessageListener(topic = "log", consumerGroup = "resultGroup")
public class OrderResultMQListener implements RocketMQListener, RocketMQPushConsumerLifecycleListener {
    @Autowired
    private IOrderService orderService;

    @Override
    public void onMessage(Object message) {

    }

    /**
     * 消息监听
     *
     * @param consumer
     */
    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt msg : msgs) {
                String result = new String(msg.getBody(), StandardCharsets.UTF_8);
                System.out.println("----------- result：" + result);
            }

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
    }
}
