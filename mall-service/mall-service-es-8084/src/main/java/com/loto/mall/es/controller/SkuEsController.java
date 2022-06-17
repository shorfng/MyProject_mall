package com.loto.mall.es.controller;

import com.loto.mall.es.service.SkuEsService;
import com.loto.mall.api.es.model.SkuEs;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-17 20:13<p>
 * PageName：SkuEsController.java<p>
 * Function：
 */

@RestController
@RequestMapping(value = "/es")
@Api(value = "SkuEsController", tags = "Sku - Es")
public class SkuEsController {
    @Autowired
    private SkuEsService skuEsService;

    @ApiOperation(value = "商品搜索")
    @GetMapping
    public RespResult<Map<String, Object>> search(@RequestParam(required = false) Map<String, Object> searchMap) {
        Map<String, Object> resultMap = skuEsService.search(searchMap);
        return RespResult.ok(resultMap);
    }

    @ApiOperation(value = "增加索引")
    @PostMapping(value = "/add")
    public RespResult add(@RequestBody SkuEs skuEs) {
        skuEsService.add(skuEs);
        return RespResult.ok();
    }

    @ApiOperation(value = "删除索引")
    @DeleteMapping(value = "/del/{id}")
    public RespResult del(@PathVariable(value = "id") String id) {
        skuEsService.del(id);
        return RespResult.ok();
    }
}
