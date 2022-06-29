package com.loto.mall.druid.controller;

import com.loto.mall.api.druid.model.HotGoods;
import com.loto.mall.druid.service.HotGoodsService;
import com.loto.mall.druid.utils.DruidPage;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @ApiOperation(value = "分页查询")
    @GetMapping(value = "/page/{page}/{size}")
    public RespResult<DruidPage<List<HotGoods>>> pageList(
            @PathVariable(value = "page") Integer page,
            @PathVariable(value = "size") Integer size) {

        DruidPage<List<HotGoods>> druidPage = hotGoodsService.pageList(size, page);
        return RespResult.ok(druidPage);
    }

    @ApiOperation(value = "分页查询 + 排序")
    @GetMapping(value = "/page/{page}/{size}/{sort}/{sortType}")
    public RespResult<DruidPage<List<HotGoods>>> pageListSort(
            @PathVariable(value = "page") Integer page,
            @PathVariable(value = "size") Integer size,
            @PathVariable(value = "sort") String sort,
            @PathVariable(value = "sortType") String sortType) {

        DruidPage<List<HotGoods>> druidPage = hotGoodsService.pageListSort(size, page, sort, sortType);
        return RespResult.ok(druidPage);
    }

    @ApiOperation(value = "时间查询（查询过去几小时前N条记录）")
    @GetMapping(value = "/searchTime/{size}/{hour}")
    public RespResult<List<HotGoods>> searchTime(@PathVariable("size") Integer size,
                                                 @PathVariable("hour") Integer hour) {

        List<HotGoods> hotGoods = hotGoodsService.searchTime(size, hour);
        return RespResult.ok(hotGoods);
    }

    @ApiOperation(value = "时间查询（排除指定数据）")
    @PostMapping(value = "/searchExclude/{size}/{hour}")
    public RespResult<List<HotGoods>> searchExclude(@PathVariable("size") Integer size,
                                                    @PathVariable("hour") Integer hour,
                                                    @RequestBody String[] urls) {

        List<HotGoods> hotGoods = hotGoodsService.searchExclude(size, hour, urls);
        return RespResult.ok(hotGoods);
    }
}
