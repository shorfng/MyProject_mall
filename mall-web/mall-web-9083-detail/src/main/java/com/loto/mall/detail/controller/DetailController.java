package com.loto.mall.detail.controller;

import com.loto.mall.detail.service.IDetailService;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-20 17:55<p>
 * PageName：DetailController.java<p>
 * Function：
 */

@RestController
@RequestMapping(value = "/detail")
@Api(value = "DetailController", tags = "商品详情页")
public class DetailController {
    @Autowired
    private IDetailService detailService;

    @ApiOperation(value = "生成商品详情的静态页")
    @GetMapping(value = "/{spuId}")
    public RespResult html(@PathVariable(value = "spuId") String spuId) throws Exception {
        detailService.html(spuId);
        return RespResult.ok();
    }
}
