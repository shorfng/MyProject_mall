package com.loto.mall.api.goods.feign;

import com.loto.mall.api.goods.model.Product;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-20 17:52<p>
 * PageName：SpuFeign.java<p>
 * Function：
 */

@FeignClient(value = "mall-goods")
public interface SpuFeign {
    @ApiOperation(value = "根据 spuId 查询商品信息")
    @GetMapping(value = "/spu/product/{id}")
    RespResult<Product> one(@PathVariable(value = "id") String id);
}
