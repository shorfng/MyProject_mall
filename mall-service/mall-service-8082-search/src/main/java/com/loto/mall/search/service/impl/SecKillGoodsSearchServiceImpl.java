package com.loto.mall.search.service.impl;

import com.loto.mall.api.search.model.SecKillGoodsEs;
import com.loto.mall.search.mapper.SecKillGoodsSearchMapper;
import com.loto.mall.search.service.SecKillGoodsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 16:14<p>
 * PageName：SecKillGoodsSearchServiceImpl.java<p>
 * Function：
 */

@Service
public class SecKillGoodsSearchServiceImpl implements SecKillGoodsSearchService {
    @Autowired
    private SecKillGoodsSearchMapper secKillGoodsSearchMapper;

    /**
     * 秒杀商品同步ES索引库
     * @param seckillGoodsEs
     */
    @Override
    public void add(SecKillGoodsEs seckillGoodsEs) {
        secKillGoodsSearchMapper.save(seckillGoodsEs);
    }
}
