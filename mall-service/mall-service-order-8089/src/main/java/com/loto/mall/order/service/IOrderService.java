package com.loto.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loto.mall.api.order.model.Order;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-21 16:41<p>
 * PageName：IOrderService.java<p>
 * Function：
 */

public interface IOrderService extends IService<Order> {
    /**
     * 生成订单
     */
    Boolean add(Order order);

    /**
     * 支付成功修改状态
     */
    int updateAfterPayStatus(String id);
}
