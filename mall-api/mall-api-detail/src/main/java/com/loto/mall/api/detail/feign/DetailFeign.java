package com.loto.mall.api.detail.feign;

import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-20 19:03<p>
 * PageName：DetailFeign.java<p>
 * Function：
 */

@FeignClient(value = "mall-web-9083-detail")
@RequestMapping(value = "/detail")
public interface DetailFeign {
    @ApiOperation(value = "生成商品详情的静态页")
    @GetMapping(value = "/{spuId}")
    RespResult html(@PathVariable(value = "spuId") String spuId) throws Exception;
}
