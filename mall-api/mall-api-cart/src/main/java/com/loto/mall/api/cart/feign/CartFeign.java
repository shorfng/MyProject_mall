package com.loto.mall.api.cart.feign;

import com.loto.mall.api.cart.model.Cart;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-21 9:31<p>
 * PageName：CartFeign.java<p>
 * Function：
 */

@FeignClient(value = "mall-service-8084-cart")
public interface CartFeign {
    @ApiOperation(value = "购物车列表")
    @GetMapping(value = "/cart/list")
    RespResult<List<Cart>> list();

    @ApiOperation(value = "结算页面 - 根据购物车的ID集合查询购物车数据")
    @PostMapping(value = "/cart/list")
    RespResult<List<Cart>> list(@RequestBody List<String> ids);

    @ApiOperation(value = "根据 ids 删除购物车数据")
    @DeleteMapping
    RespResult delete(@RequestBody List<String> ids);
}
