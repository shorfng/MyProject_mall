package com.loto.mall.api.goods.feign;

import com.loto.mall.api.goods.model.Category;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-20 17:47<p>
 * PageName：CategoryFeign.java<p>
 * Function：
 */

@FeignClient(value = "mall-service-8081-goods")
public interface CategoryFeign {
    @ApiOperation(value = "根据分类查询分类信息")
    @GetMapping(value = "/category/{id}")
    RespResult<Category> one(@PathVariable(value = "id") Integer id);
}
