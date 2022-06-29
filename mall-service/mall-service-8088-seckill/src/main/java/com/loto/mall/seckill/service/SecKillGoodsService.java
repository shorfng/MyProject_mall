package com.loto.mall.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loto.mall.api.seckill.model.SecKillGoods;

import java.util.List;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 14:44<p>
 * PageName：SecKillGoodsService.java<p>
 * Function：
 */

public interface SecKillGoodsService extends IService<SecKillGoods> {
    /**
     * 根据活动ID查询商品信息
     * @param acid
     * @return
     */
    List<SecKillGoods> actGoods(String acid);


    /**
     * 热门商品分离
     * @param uri 商品ID
     */
    void isolation(String uri);
}
