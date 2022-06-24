package com.loto.mall.order.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.loto.mall.api.order.model.Order;
import com.loto.mall.api.order.model.OrderRefund;
import com.loto.mall.order.pay.WeiXinPayParam;
import com.loto.mall.order.service.IOrderService;
import com.loto.mall.util.common.RespCode;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-21 16:41<p>
 * PageName：OrderController.java<p>
 * Function：
 */

@RestController
@RequestMapping(value = "/order")
@Api(value = "OrderController", tags = "订单")
@CrossOrigin
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @Autowired
    private WeiXinPayParam weiXinPayParam;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @ApiOperation(value = "生成订单")
    @PostMapping
    public RespResult add(@RequestBody Order order, HttpServletRequest request) throws Exception {
        // 用户名字
        order.setUsername("TD");

        // 下单
        Boolean bo = orderService.add(order);
        String ciphertext = weiXinPayParam.weiXinParam(order, request);
        return bo ? RespResult.ok(ciphertext) : RespResult.error(RespCode.ERROR);
    }

    @ApiOperation(value = "申请取消订单（模拟测试退款的订单）")
    @PutMapping(value = "/refund/{id}")
    public RespResult refund(@PathVariable(value = "id") String id, HttpServletRequest request) throws Exception {
        // 用户名
        String username = "TD";

        // 查询订单，是否符合退款要求
        Order order = orderService.getById(id);
        if (order.getPayStatus() == 1 && order.getOrderStatus() == 1) {
            // 添加退款记录,更新订单状态
            OrderRefund orderRefund = new OrderRefund(
                    IdWorker.getIdStr(),
                    id,
                    1,
                    null,
                    username,
                    0, // 申请退款
                    new Date(),
                    order.getMoneys()
            );

            // 退款操作
            orderService.refund(orderRefund);

            // 向MQ发消息（申请退款）
            Message message = MessageBuilder.withPayload(weiXinPayParam.weiXinRefundParam(orderRefund)).build();
            TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction("refundtx", "refund", message, orderRefund);

            if (transactionSendResult.getSendStatus() == SendStatus.SEND_OK) {
                return RespResult.ok();
            }
        }

        // 不符合直接返回错误
        return RespResult.error("当前订单不符合取消操作要求！");
    }
}
