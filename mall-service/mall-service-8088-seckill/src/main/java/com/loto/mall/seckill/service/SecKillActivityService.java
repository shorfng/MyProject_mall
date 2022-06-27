package com.loto.mall.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loto.mall.api.seckill.model.SecKillActivity;

import java.util.List;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 14:43<p>
 * PageName：SecKillActivityService.java<p>
 * Function：
 */

public interface SecKillActivityService extends IService<SecKillActivity> {
    /**
     * 查询有效活动列表
     * @return
     */
    List<SecKillActivity> validActivity();
}
