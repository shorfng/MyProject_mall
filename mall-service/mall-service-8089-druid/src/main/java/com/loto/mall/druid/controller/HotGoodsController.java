package com.loto.mall.druid.controller;

import com.loto.mall.api.druid.model.HotGoods;
import com.loto.mall.druid.service.HotGoodsService;
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
 * Date：2022-06-29 15:35<p>
 * PageName：HotGoodsController.java<p>
 * Function：
 */

@RestController
@RequestMapping(value = "/hot/goods")
@Api(value = "HotGoodsController", tags = "热门商品数据")
public class HotGoodsController {
    @Autowired
    private HotGoodsService hotGoodsService;

    @ApiOperation(value = "查询所有")
    @GetMapping
    public RespResult<List<HotGoods>> list() {
        List<HotGoods> list = hotGoodsService.list();
        return RespResult.ok(list);
    }

    @ApiOperation(value = "查询前 N 条记录")
    @GetMapping(value = "/top/{size}")
    public RespResult<List<HotGoods>> topNum(@PathVariable(value = "size") Integer size) {
        List<HotGoods> hotGoods = hotGoodsService.topNum(size);
        return RespResult.ok(hotGoods);
    }
}
