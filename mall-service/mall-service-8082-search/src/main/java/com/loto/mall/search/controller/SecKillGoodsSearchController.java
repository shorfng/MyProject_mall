package com.loto.mall.search.controller;

import com.loto.mall.api.search.model.SecKillGoodsEs;
import com.loto.mall.search.service.SecKillGoodsSearchService;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 16:12<p>
 * PageName：SecKillGoodsSearchController.java<p>
 * Function：
 */

@RestController
@RequestMapping(value = "/seckill/goods")
@Api(value = "SecKillGoodsSearchController", tags = "秒杀-商品")
public class SecKillGoodsSearchController {
    @Autowired
    private SecKillGoodsSearchService secKillGoodsSearchService;

    @ApiOperation(value = "秒杀商品同步ES索引库")
    @PostMapping(value = "/import")
    public RespResult add(@RequestBody SecKillGoodsEs seckillGoodsEs) {
        secKillGoodsSearchService.add(seckillGoodsEs);
        return RespResult.ok();
    }
}
