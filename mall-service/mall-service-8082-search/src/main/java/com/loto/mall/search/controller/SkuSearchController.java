package com.loto.mall.search.controller;

import com.loto.mall.search.service.SkuSearchService;
import com.loto.mall.api.search.model.SkuSearch;
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
 * PageName：SkuSearchController.java<p>
 * Function：
 */

@RestController
@RequestMapping(value = "/search")
@Api(value = "SkuSearchController", tags = "Sku搜索")
public class SkuSearchController {
    @Autowired
    private SkuSearchService skuSearchService;

    @ApiOperation(value = "增加索引")
    @PostMapping(value = "/add")
    public RespResult add(@RequestBody SkuSearch skuSearch) {
        skuSearchService.add(skuSearch);
        return RespResult.ok();
    }

    @ApiOperation(value = "删除索引")
    @DeleteMapping(value = "/del/{id}")
    public RespResult del(@PathVariable(value = "id") String id) {
        skuSearchService.del(id);
        return RespResult.ok();
    }

    @ApiOperation(value = "商品搜索")
    @GetMapping
    public RespResult<Map<String, Object>> search(@RequestParam(required = false) Map<String, Object> searchMap) {
        Map<String, Object> resultMap = skuSearchService.search(searchMap);
        return RespResult.ok(resultMap);
    }
}
