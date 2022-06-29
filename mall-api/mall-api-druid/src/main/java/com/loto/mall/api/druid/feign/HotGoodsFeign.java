package com.loto.mall.api.druid.feign;

import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-29 18:05<p>
 * PageName：HotGoodsFeign.java<p>
 * Function：
 */

@FeignClient(value = "mall-service-8089-druid")
public interface HotGoodsFeign {
    @ApiOperation(value = "查询热门商品（最近1小时内，根据查询数量排序，如果已经是分析过的热门商品，需要把它排除）")
    @PostMapping(value = "/hot/goods/searchHotGoods/{size}/{hour}/{max}")
    RespResult<List<Map<String, String>>> searchHotGoods(@PathVariable("size") Integer size,
                                                         @PathVariable("hour") Integer hour,
                                                         @PathVariable("max") Integer max,
                                                         @RequestBody String[] urls);
}
