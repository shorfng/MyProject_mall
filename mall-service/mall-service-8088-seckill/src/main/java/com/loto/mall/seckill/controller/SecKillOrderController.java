package com.loto.mall.seckill.controller;

import com.loto.mall.seckill.service.SecKillOrderService;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-29 20:57<p>
 * PageName：SecKillOrderController.java<p>
 * Function：
 */

@RestController
@RequestMapping(value = "/seckill/order")
@Api(value = "SecKillOrderController", tags = "秒杀-订单")
public class SecKillOrderController {
    @Autowired
    private SecKillOrderService secKillOrderService;

    @ApiOperation(value = "非热门抢单")
    @PostMapping
    public RespResult add(String username, String id, Integer num) {
        return RespResult.ok("抢单成功");
    }
}
