package com.loto.mall.order.listener;

import com.loto.mall.api.order.model.OrderRefund;
import com.loto.mall.order.mapper.OrderRefundMapper;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-24 18:51<p>
 * PageName：RefundTransactionMQListener.java<p>
 * Function：
 */

@Component
@RocketMQTransactionListener(txProducerGroup = "refundtx")
public class RefundTransactionMQListener implements RocketMQLocalTransactionListener {
    @Autowired
    private OrderRefundMapper orderRefundMapper;

    /**
     * 变更订单状态，记录退款申请信息
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        try {
            // 退款申请记录
            OrderRefund orderRefund = (OrderRefund) arg;
            orderRefundMapper.deleteById(orderRefund.getId());  // 先删除
            int count = orderRefundMapper.insert(orderRefund);  // 再插入
            if (count <= 0) {
                return RocketMQLocalTransactionState.ROLLBACK;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RocketMQLocalTransactionState.ROLLBACK;
        }

        return RocketMQLocalTransactionState.COMMIT;
    }

    /**
     * 消息回查
     *
     * @param msg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        return RocketMQLocalTransactionState.COMMIT;
    }
}
