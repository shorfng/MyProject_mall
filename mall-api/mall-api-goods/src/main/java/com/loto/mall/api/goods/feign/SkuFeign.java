package com.loto.mall.api.goods.feign;

import com.loto.mall.api.cart.model.Cart;
import com.loto.mall.api.goods.model.Sku;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-16 0:01<p>
 * PageName：SkuFeign.java<p>
 * Function：
 */

@FeignClient(value = "mall-service-8081-goods")    //服务名字
public interface SkuFeign {
    @ApiOperation(value = "根据商品推广分类id，查询指定分类下的产品列表")
    @GetMapping(value = "/sku/aditems/type")
    List<Sku> typeItems(@RequestParam(value = "typeId") Integer typeId);

    @ApiOperation(value = "删除缓存（根据商品推广分类id，查询指定分类下的产品列表）")
    @DeleteMapping(value = "/sku/aditems/type")
    RespResult delTypeItems(@RequestParam(value = "typeId") Integer typeId);

    @ApiOperation(value = "修改缓存（根据商品推广分类id，查询指定分类下的产品列表）")
    @PutMapping(value = "/sku/aditems/type")
    List<Sku> updateTypeItems(@RequestParam(value = "typeId") Integer typeId);

    @ApiOperation(value = "根据ID查询Sku商品详情")
    @GetMapping(value = "/sku/{id}")
    RespResult<Sku> one(@PathVariable(value = "id") String id);

    @ApiOperation(value = "库存递减")
    @PostMapping(value = "/sku/deleteCount")
    RespResult deleteCount(@RequestBody List<Cart> carts);
}
