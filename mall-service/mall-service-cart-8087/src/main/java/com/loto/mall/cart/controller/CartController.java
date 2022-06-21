package com.loto.mall.cart.controller;

import com.loto.mall.api.cart.model.Cart;
import com.loto.mall.cart.service.ICartService;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-21 9:46<p>
 * PageName：CartController.java<p>
 * Function：
 */

@RestController
@RequestMapping(value = "/cart")
@Api(value = "CartController", tags = "购物车")
@CrossOrigin
public class CartController {
    @Autowired
    private ICartService cartService;

    @ApiOperation(value = "加入购物车")
    @GetMapping(value = "/{id}/{num}")
    public RespResult add(@PathVariable(value = "id") String id,
                          @PathVariable(value = "num") Integer num) {

        String userName = "TD";
        cartService.add(id, userName, num);
        return RespResult.ok();
    }

    @ApiOperation(value = "购物车列表")
    @GetMapping(value = "/list")
    public RespResult<List<Cart>> list() {
        String userName = "TD";
        List<Cart> list = cartService.list(userName);
        return RespResult.ok(list);
    }

    @ApiOperation(value = "结算页面 - 根据购物车的ID集合查询购物车数据")
    @PostMapping(value = "/list")
    public RespResult<List<Cart>> list(@RequestBody List<String> ids){
        List<Cart> carts = cartService.list(ids);
        return RespResult.ok(carts);
    }
}
