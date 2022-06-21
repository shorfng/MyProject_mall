package com.loto.mall.cart.controller;

import com.loto.mall.cart.service.ICartService;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-21 9:46<p>
 * PageName：CartController.java<p>
 * Function：
 */

@RestController
@RequestMapping(value = "/cart")
@Api(value = "CartController", tags = "购物车")
public class CartController {
    @Autowired
    private ICartService ICartService;

    @ApiOperation(value = "加入购物车")
    @GetMapping(value = "/{id}/{num}")
    public RespResult add(@PathVariable(value = "id") String id,
                          @PathVariable(value = "num") Integer num) {

        String userName = "TD";
        ICartService.add(id, userName, num);
        return RespResult.ok();
    }
}
