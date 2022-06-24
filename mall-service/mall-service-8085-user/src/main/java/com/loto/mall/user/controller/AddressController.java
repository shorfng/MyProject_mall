package com.loto.mall.user.controller;

import com.loto.mall.api.user.model.Address;
import com.loto.mall.user.service.IAddressService;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-21 12:39<p>
 * PageName：AddressController.java<p>
 * Function：
 */

@RestController
@RequestMapping(value = "/address")
@Api(value = "AddressController", tags = "收件地址")
@CrossOrigin
public class AddressController {
    @Autowired
    private IAddressService addressService;

    @ApiOperation(value = "查询用户收件地址列表")
    @GetMapping(value = "/list")
    public RespResult<List<Address>> list() {
        String userName = "TD";
        List<Address> list = addressService.list(userName);
        return RespResult.ok(list);
    }
}
