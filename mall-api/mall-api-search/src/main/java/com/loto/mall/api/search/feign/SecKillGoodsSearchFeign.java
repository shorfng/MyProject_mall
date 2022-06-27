package com.loto.mall.api.search.feign;

import com.loto.mall.api.search.model.SecKillGoodsEs;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 16:20<p>
 * PageName：SecKillGoodsSearchFeign.java<p>
 * Function：
 */

@FeignClient(value = "mall-service-8082-search")
public interface SecKillGoodsSearchFeign {
    @ApiOperation(value = "秒杀商品同步ES索引库")
    @PostMapping("/seckill/goods/import")
    RespResult add(@RequestBody SecKillGoodsEs seckillGoodsEs);
}
