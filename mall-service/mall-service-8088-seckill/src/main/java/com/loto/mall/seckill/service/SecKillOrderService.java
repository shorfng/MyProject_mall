package com.loto.mall.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loto.mall.api.seckill.model.SecKillOrder;

import java.util.Map;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 14:44<p>
 * PageName：SecKillOrderService.java<p>
 * Function：
 */

public interface SecKillOrderService extends IService<SecKillOrder> {
    /**
     * 非热门抢单
     * @param dataMap
     * @return
     */
    int add(Map<String,Object> dataMap);
}
