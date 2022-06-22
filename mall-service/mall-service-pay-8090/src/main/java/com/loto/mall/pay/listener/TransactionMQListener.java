package com.loto.mall.pay.listener;

import com.alibaba.fastjson.JSON;
import com.loto.mall.api.pay.model.PayLog;
import com.loto.mall.pay.service.IPayLogService;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-22 19:19<p>
 * PageName：TransactionMQListener.java<p>
 * Function：
 */

@Component
@RocketMQTransactionListener(txProducerGroup = "rocket")
public class TransactionMQListener implements RocketMQLocalTransactionListener {
    @Autowired
    private IPayLogService payLogService;

    /**
     * 当向 RocketMQ 的 Broker 发送 Half 消息成功之后，调用该方法
     *
     * @param msg 发送的消息
     * @param arg 额外参数
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        try {
            // 本地事务控制
            String result = new String((byte[]) msg.getPayload(), StandardCharsets.UTF_8);
            PayLog payLog = JSON.parseObject(result, PayLog.class);
            payLogService.add(payLog);
        } catch (Exception e) {
            // 异常，则回滚
            e.printStackTrace();
            return RocketMQLocalTransactionState.ROLLBACK;
        }

        // 无异常，则提交
        return RocketMQLocalTransactionState.COMMIT;
    }

    /**
     * 超时回查
     *
     * @param msg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        return RocketMQLocalTransactionState.COMMIT;
    }
}
