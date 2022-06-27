package com.loto.mall.api.seckill.feign;

import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 17:15<p>
 * PageName：SecKillPageFeign.java<p>
 * Function：
 */

@FeignClient(value = "mall-web-9088-seckill")
@RequestMapping(value = "/page")
public interface SecKillPageFeign {
    @ApiOperation(value = "秒杀商品详情页 - 生成")
    @GetMapping(value = "/seckill/goods/{id}")
    RespResult page(@PathVariable("id") String id) throws Exception;
}
