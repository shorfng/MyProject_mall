package com.loto.mall.search.controller;

import com.loto.mall.api.search.feign.SkuSearchFeign;
import com.loto.mall.util.common.RespResult;
import com.loto.mall.util.common.UrlUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-19 19:38<p>
 * PageName：SearchController.java<p>
 * Function：
 */

@Controller
@RequestMapping(value = "/web/search")
@Api(value = "SearchController", tags = "商品搜索")
public class SearchController {
    @Autowired
    private SkuSearchFeign skuSearchFeign;

    @ApiOperation(value = "商品搜索")
    @GetMapping
    public String search(@RequestParam(required = false) Map<String, Object> searchMap, Model model) {
        // 搜索
        RespResult<Map<String, Object>> resultMap = skuSearchFeign.search(searchMap);

        // 组装用户访问的 url
        model.addAttribute("url", UrlUtils.map2url("/web/search", searchMap, "page"));
        model.addAttribute("urlSort", UrlUtils.map2url("/web/search", searchMap, "orderType", "orderField", "page"));
        // 结果列表
        model.addAttribute("result", resultMap.getData());
        // 搜索框
        model.addAttribute("searchMap", searchMap);
        return "search";
    }
}
