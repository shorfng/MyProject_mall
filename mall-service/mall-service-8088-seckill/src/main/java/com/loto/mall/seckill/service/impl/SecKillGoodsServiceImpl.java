package com.loto.mall.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loto.mall.api.seckill.model.SecKillGoods;
import com.loto.mall.seckill.mapper.SecKillGoodsMapper;
import com.loto.mall.seckill.service.SecKillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private RedisTemplate redisTemplate;

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

    /**
     * 热门商品分离
     *
     * @param uri 商品ID
     */
    @Override
    public void isolation(String uri) {
        // 锁定商品
        QueryWrapper<SecKillGoods> secKillGoodsQueryWrapper = new QueryWrapper<>();
        secKillGoodsQueryWrapper.eq("id", uri);
        secKillGoodsQueryWrapper.eq("is_lock", 0);
        secKillGoodsQueryWrapper.gt("store_count", 0);

        SecKillGoods secKillGoods = new SecKillGoods();
        secKillGoods.setIsLock(1);

        int count = secKillGoodsMapper.update(secKillGoods, secKillGoodsQueryWrapper);

        // 热门商品分离 -> 查询出来存入到Redis缓存
        if (count > 0) {
            // 查询商品个数
            secKillGoods = secKillGoodsMapper.selectById(uri);

            // 存入 Redis
            redisTemplate.boundHashOps("HotSeckillGoods").increment(uri, secKillGoods.getStoreCount());
        }
    }
}
