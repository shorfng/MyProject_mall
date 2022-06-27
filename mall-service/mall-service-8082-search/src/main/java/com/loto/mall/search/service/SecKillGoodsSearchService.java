package com.loto.mall.search.service;

import com.loto.mall.api.search.model.SecKillGoodsEs;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 16:13<p>
 * PageName：SecKillGoodsSearchService.java<p>
 * Function：
 */

public interface SecKillGoodsSearchService {
    /**
     * 秒杀商品同步ES索引库
     * @param seckillGoodsEs
     */
    void add(SecKillGoodsEs seckillGoodsEs);
}
