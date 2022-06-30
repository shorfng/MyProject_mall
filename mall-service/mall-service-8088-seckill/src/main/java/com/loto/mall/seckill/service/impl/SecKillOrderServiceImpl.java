package com.loto.mall.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loto.mall.api.seckill.model.SecKillGoods;
import com.loto.mall.api.seckill.model.SecKillOrder;
import com.loto.mall.seckill.mapper.SecKillGoodsMapper;
import com.loto.mall.seckill.mapper.SecKillOrderMapper;
import com.loto.mall.seckill.service.SecKillOrderService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-29 21:01<p>
 * PageName：SecKillOrderServiceImpl.java<p>
 * Function：
 */

@Service
public class SecKillOrderServiceImpl extends ServiceImpl<SecKillOrderMapper, SecKillOrder> implements SecKillOrderService {
    public static final int STORE_NOT_FULL = 0;             // 库存不足
    public static final int STORE_FULL_ORDER_SUCCESS = 1;   // 库存足够下单成功

    @Autowired
    private SecKillOrderMapper secKillOrderMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SecKillGoodsMapper secKillGoodsMapper;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public int add(Map<String, Object> dataMap) {
        String username = dataMap.get("username").toString();
        String id = dataMap.get("id").toString();
        Integer num = Integer.valueOf(dataMap.get("num").toString());

        // 获取锁
        RLock lock = redissonClient.getLock("No00001");
        lock.lock();

        try {
            // 库存足够
            Object storeCount = redisTemplate.boundHashOps("HotSeckillGoods").get(id);
            if (storeCount == null || Integer.valueOf(storeCount.toString()) < num) {
                // 移除排队标识
                redisTemplate.delete("OrderQueue" + username);
                return STORE_NOT_FULL;
            }

            // 查询商品信息
            SecKillGoods secKillGoods = secKillGoodsMapper.selectById(id);

            // 添加订单
            SecKillOrder secKillOrder = new SecKillOrder();
            secKillOrder.setUsername(username);
            secKillOrder.setSeckillGoodsId(id);
            secKillOrder.setCreateTime(new Date());
            secKillOrder.setMoney(secKillGoods.getSeckillPrice() * num);
            secKillOrder.setNum(num);
            secKillOrder.setStatus(0);  //下单了
            secKillOrderMapper.insert(secKillOrder);

            // 库存递减
            Long lastStoreCount = redisTemplate.boundHashOps("HotSeckillGoods").increment(id, -num);

            if (lastStoreCount == 0) {
                // 将数据同步到数据库
                secKillGoods = new SecKillGoods();
                secKillGoods.setId(id);
                secKillGoods.setStoreCount(0);

                // 将当前商品添加到Redis布隆过滤器 -> 作业
                // 用户下次抢购该商品，去布隆过滤器中判断该商品是否在布隆过滤器中，如果在，则表明售罄
                secKillGoodsMapper.updateById(secKillGoods);

                // 删除Redis缓存
                redisTemplate.boundHashOps("HotSeckillGoods").delete(id);
            }

            // 无论排队成功或失败，都会移除排队标识
            redisTemplate.delete("OrderQueue" + username);
            lock.unlock();
        } catch (NumberFormatException e) {
            lock.unlock();
        }

        return STORE_FULL_ORDER_SUCCESS;
    }
}
