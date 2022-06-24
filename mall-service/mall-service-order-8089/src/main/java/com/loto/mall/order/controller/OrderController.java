package com.loto.mall.order.controller;

import com.loto.mall.api.order.model.Order;
import com.loto.mall.order.pay.WeiXinPayParam;
import com.loto.mall.order.service.IOrderService;
import com.loto.mall.util.common.RespCode;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
}
