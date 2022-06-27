package com.loto.mall.seckill.controller;

import com.loto.mall.api.seckill.model.SecKillActivity;
import com.loto.mall.seckill.service.SecKillActivityService;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 14:39<p>
 * PageName：SecKillActivityController.java<p>
 * Function：
 */

@RestController
@RequestMapping(value = "/activity")
@Api(value = "SecKillActivityController", tags = "秒杀-活动")
public class SecKillActivityController {
    @Autowired
    private SecKillActivityService secKillActivityService;

    @ApiOperation(value = "查询有效活动列表")
    @GetMapping
    public RespResult list() {
        // 查询有效活动列表
        List<SecKillActivity> seckillActivities = secKillActivityService.validActivity();
        return RespResult.ok(seckillActivities);
    }
}
