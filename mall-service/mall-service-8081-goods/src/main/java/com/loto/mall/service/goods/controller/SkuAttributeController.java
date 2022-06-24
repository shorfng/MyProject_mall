package com.loto.mall.service.goods.controller;

import com.loto.mall.api.goods.model.SkuAttribute;
import com.loto.mall.service.goods.service.ISkuAttributeService;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 属性表 前端控制器
 * </p>
 *
 * @author 蓝田_Loto
 * @since 2022-06-13 22:42:39
 */
@RestController
@RequestMapping("/skuAttribute")
@Api(value = "SkuAttributeController", tags = "Sku属性")
public class SkuAttributeController {
    @Autowired
    private ISkuAttributeService skuAttributeService;

    @ApiOperation(value = "查询属性集合（根据分类ID）")
    @GetMapping(value = "/category/{id}")
    public RespResult<List<SkuAttributeController>> categorySkuAttributeList(@PathVariable(value = "id") Integer id) {
        List<SkuAttribute> skuAttributes = skuAttributeService.queryList(id);
        return RespResult.ok(skuAttributes);
    }
}
