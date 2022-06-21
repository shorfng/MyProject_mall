package com.loto.mall.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loto.mall.api.cart.feign.CartFeign;
import com.loto.mall.api.cart.model.Cart;
import com.loto.mall.api.goods.feign.SkuFeign;
import com.loto.mall.api.order.model.Order;
import com.loto.mall.api.order.model.OrderSku;
import com.loto.mall.order.mapper.OrderMapper;
import com.loto.mall.order.mapper.OrderSkuMapper;
import com.loto.mall.order.service.IOrderService;
import com.loto.mall.util.common.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-21 16:41<p>
 * PageName：OrderServiceImpl.java<p>
 * Function：
 */

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderSkuMapper orderSkuMapper;

    @Autowired
    private CartFeign cartFeign;

    @Autowired
    private SkuFeign skuFeign;

    /**
     * 生成订单
     */
    @Override
    public Boolean add(Order order) {
        order.setId(IdWorker.getIdStr());   // MybatisPlus 提供的自动生成id
        order.setCreateTime(new Date());    // 创建时间
        order.setUpdateTime(order.getCreateTime()); // 更新时间

        // 1、查询购物车数据
        RespResult<List<Cart>> cartResp = cartFeign.list(order.getCartIds());
        List<Cart> carts = cartResp.getData();
        if (carts == null || carts.size() == 0) {
            return false;
        }

        // 2、递减库存
        skuFeign.deleteCount(carts);

        // 3、添加订单明细
        int totalNum = 0; // 订单商品总数量
        int moneys = 0;   // 支付总金额
        for (Cart cart : carts) {
            // 将Cart转成OrderSku
            OrderSku orderSku = JSON.parseObject(JSON.toJSONString(cart), OrderSku.class);
            orderSku.setId(IdWorker.getIdStr()); // MybatisPlus 提供的自动生成id
            orderSku.setOrderId(order.getId());  // 提前赋值
            orderSku.setMoney(orderSku.getPrice() * orderSku.getNum());

            // 添加
            orderSkuMapper.insert(orderSku);

            // 统计计算
            totalNum += orderSku.getNum();
            moneys += orderSku.getMoney();
        }

        order.setTotalNum(totalNum);
        order.setMoneys(moneys);

        // 至此，order 订单对象赋值完毕

        // 4、添加订单
        orderMapper.insert(order);

        //Exception--->TestTransaction
        //int q=10/0;

        // 5、删除购物车数据
        cartFeign.delete(order.getCartIds());
        return true;
    }
}
