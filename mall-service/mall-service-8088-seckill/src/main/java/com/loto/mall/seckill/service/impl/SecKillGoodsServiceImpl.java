package com.loto.mall.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loto.mall.api.seckill.model.SecKillGoods;
import com.loto.mall.seckill.mapper.SecKillGoodsMapper;
import com.loto.mall.seckill.service.SecKillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 17:03<p>
 * PageName：SecKillGoodsServiceImpl.java<p>
 * Function：
 */

@Service
public class SecKillGoodsServiceImpl extends ServiceImpl<SecKillGoodsMapper, SecKillGoods> implements SecKillGoodsService {
    @Autowired
    private SecKillGoodsMapper secKillGoodsMapper;

    /**
     * 根据活动ID查询商品信息
     *
     * @param acid
     * @return
     */
    @Override
    public List<SecKillGoods> actGoods(String acid) {
        QueryWrapper<SecKillGoods> secKillGoodsQueryWrapper = new QueryWrapper<SecKillGoods>();
        secKillGoodsQueryWrapper.eq("activity_id", acid);
        return secKillGoodsMapper.selectList(secKillGoodsQueryWrapper);
    }
}
