package com.loto.mall.seckill.controller;

import com.loto.mall.api.seckill.feign.SecKillGoodsFeign;
import com.loto.mall.api.seckill.model.SecKillGoods;
import com.loto.mall.seckill.service.SecKillPageService;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 16:51<p>
 * PageName：SecKillPageController.java<p>
 * Function：
 */

@RestController
@RequestMapping(value = "/seckill/page")
@Api(value = "SecKillPageController", tags = "秒杀商品详情页")
public class SecKillPageController {
    @Autowired
    private SecKillPageService secKillPageService;

    @Autowired
    private SecKillGoodsFeign secKillGoodsFeign;

    @ApiOperation(value = "秒杀商品详情页 - 生成")
    @GetMapping(value = "/general/{id}")
    public RespResult general(@PathVariable("id") String id) throws Exception {
        // 生成秒杀商品详情页
        secKillPageService.html(id);
        return RespResult.ok();
    }

    @ApiOperation(value = "秒杀商品详情页 - 删除指定活动的页面")
    @DeleteMapping(value = "/deleByAct/{acid}")
    public RespResult deleByAct(@PathVariable("acid") String acid) {
        // 查询当前活动ID对应的商品列表数据
        RespResult<List<SecKillGoods>> listRespResult = secKillGoodsFeign.actGoods(acid);
        List<SecKillGoods> goodsList = listRespResult.getData();

        // 根据列表数据删除页面
        if (goodsList != null) {
            // 循环删除页面
            for (SecKillGoods secKillGoods : goodsList) {
                secKillPageService.delete(secKillGoods.getId());
            }
        }

        return RespResult.ok();
    }
}
