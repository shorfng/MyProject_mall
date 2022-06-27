package com.loto.mall.seckill.controller;

import com.loto.mall.seckill.service.SecKillPageService;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 16:51<p>
 * PageName：SecKillPageController.java<p>
 * Function：
 */

@RestController
@RequestMapping(value = "/page")
public class SecKillPageController {
    @Autowired
    private SecKillPageService secKillPageService;

    @ApiOperation(value = "秒杀商品详情页 - 生成")
    @GetMapping(value = "/seckill/goods/{id}")
    public RespResult page(@PathVariable("id") String id) throws Exception {
        // 生成秒杀商品详情页
        secKillPageService.html(id);
        return RespResult.ok();
    }
}
