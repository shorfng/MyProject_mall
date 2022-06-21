package com.loto.mall.api.cart.feign;

import com.loto.mall.api.cart.model.Cart;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-21 9:31<p>
 * PageName：CartFeign.java<p>
 * Function：
 */

@FeignClient(value = "mall-cart")
@RequestMapping(value = "/cart")
public interface CartFeign {
    @ApiOperation(value = "购物车列表")
    @GetMapping(value = "/list")
    RespResult<List<Cart>> list();
}
