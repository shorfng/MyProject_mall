package com.loto.mall.service.goods.controller;

import com.loto.mall.api.goods.model.Sku;
import com.loto.mall.service.goods.service.ISkuService;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 商品表（每个产品信息） 前端控制器
 * </p>
 *
 * @author 蓝田_Loto
 * @since 2022-06-13 22:42:39
 */
@RestController
@RequestMapping("/sku")
@Api(value = "SkuController", tags = "商品管理（每个产品信息）")
public class SkuController {
    @Autowired
    private ISkuService skuService;

    @ApiOperation(value = "根据商品推广分类id，查询指定分类下的产品列表")
    @GetMapping(value = "/aditems/type")
    public List<Sku> typeItems(@RequestParam(value = "typeId") Integer typeId) {
        // 查询
        List<Sku> adSkuItems = skuService.typeSkuItems(typeId);
        return adSkuItems;
    }

    @ApiOperation(value = "删除缓存（根据商品推广分类id，查询指定分类下的产品列表）")
    @DeleteMapping(value = "/aditems/type")
    public RespResult delTypeItems(@RequestParam(value = "typeId") Integer typeId) {
        skuService.delTypeItems(typeId);
        return RespResult.ok();
    }

    @ApiOperation(value = "修改缓存（根据商品推广分类id，查询指定分类下的产品列表）")
    @PutMapping(value = "/aditems/type")
    public List<Sku> updateTypeItems(@RequestParam(value = "typeId") Integer typeId) {
        List<Sku> adSkuItems = skuService.updateTypeItems(typeId);
        return adSkuItems;
    }
}
