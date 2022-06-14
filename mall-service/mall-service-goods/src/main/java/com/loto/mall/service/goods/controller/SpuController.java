package com.loto.mall.service.goods.controller;

import com.loto.mall.api.goods.model.Product;
import com.loto.mall.service.goods.service.ISpuService;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品表（同类产品信息） 前端控制器
 * </p>
 *
 * @author 蓝田_Loto
 * @since 2022-06-13 22:42:39
 */
@RestController
@RequestMapping("/spu")
@Api(value = "SpuController", tags = "同类产品信息")
public class SpuController {
    @Autowired
    private ISpuService spuService;

    @ApiOperation(value = "保存/修改同类产品信息")
    @PostMapping(value = "/save")
    public RespResult save(@RequestBody Product product) {
        spuService.saveProduct(product);
        return RespResult.ok();
    }
}
