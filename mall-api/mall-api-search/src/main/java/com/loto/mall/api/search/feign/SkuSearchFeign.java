package com.loto.mall.api.search.feign;

import com.loto.mall.api.search.model.SkuSearch;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-17 20:17<p>
 * PageName：SkuSearchFeign.java<p>
 * Function：
 */

@FeignClient(value = "mall-service-8082-search")
public interface SkuSearchFeign {
    @ApiOperation(value = "增加索引")
    @PostMapping(value = "/search/add")
    RespResult add(@RequestBody SkuSearch skuSearch);

    @ApiOperation(value = "删除索引")
    @DeleteMapping(value = "/search/del/{id}")
    RespResult del(@PathVariable(value = "id") String id);

    @ApiOperation(value = "商品搜索")
    @GetMapping("/search")
    RespResult<Map<String, Object>> search(@RequestParam(required = false) Map<String, Object> searchMap);

}
