package com.loto.mall.api.seckill.feign;

import com.loto.mall.api.seckill.model.SecKillGoods;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 16:57<p>
 * PageName：SecKillGoodsFeign.java<p>
 * Function：
 */

@FeignClient(value = "mall-service-8088-seckill")
@RequestMapping(value = "/seckill/goods")
public interface SecKillGoodsFeign {
    @ApiOperation(value = "根据ID查询秒杀商品详情")
    @GetMapping(value = "/{id}")
    RespResult<SecKillGoods> one(@PathVariable("id") String id);
}
