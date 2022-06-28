package com.loto.mall.seckill.controller;

import com.loto.mall.api.seckill.model.SecKillGoods;
import com.loto.mall.seckill.service.SecKillGoodsService;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 17:00<p>
 * PageName：SecKillGoodsController.java<p>
 * Function：
 */

@RestController
@RequestMapping(value = "/seckill/goods")
@Api(value = "SecKillGoodsController", tags = "秒杀-商品")
public class SecKillGoodsController {
    @Autowired
    private SecKillGoodsService secKillGoodsService;

    @ApiOperation(value = "根据ID查询秒杀商品详情")
    @GetMapping(value = "/{id}")
    public RespResult<SecKillGoods> one(@PathVariable("id") String id) {
        SecKillGoods seckillGoods = secKillGoodsService.getById(id);
        return RespResult.ok(seckillGoods);
    }

    @ApiOperation(value = "根据活动查询秒杀商品集合")
    @GetMapping(value = "/act/{acid}")
    public RespResult<List<SecKillGoods>> actGoods(@PathVariable("acid") String acid) {
        List<SecKillGoods> secKillGoods = secKillGoodsService.actGoods(acid);
        return RespResult.ok(secKillGoods);
    }

}
