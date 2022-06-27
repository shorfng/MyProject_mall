package com.loto.mall.canal.listener;

import com.alibaba.fastjson.JSON;
import com.loto.mall.api.search.feign.SecKillGoodsSearchFeign;
import com.loto.mall.api.search.model.SecKillGoodsEs;
import com.loto.mall.api.seckill.feign.SecKillPageFeign;
import com.loto.mall.api.seckill.model.SecKillGoods;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 18:21<p>
 * PageName：SecKillGoodsHandler.java<p>
 * Function：
 */

@Component
@CanalTable(value = "seckill_goods")
public class SecKillGoodsHandler implements EntryHandler<SecKillGoods> {
    @Autowired
    private SecKillGoodsSearchFeign secKillGoodsSearchFeign;

    @Autowired
    private SecKillPageFeign secKillPageFeign;

    /**
     * 增加商品
     *
     * @param SecKillGoods
     */
    @SneakyThrows
    @Override
    public void insert(SecKillGoods SecKillGoods) {
        // 索引导入
        secKillGoodsSearchFeign.add(JSON.parseObject(JSON.toJSONString(SecKillGoods), SecKillGoodsEs.class));

        // 生成/更新静态页
        secKillPageFeign.general(SecKillGoods.getId());
    }

    /**
     * 修改商品
     *
     * @param before
     * @param after
     */
    @SneakyThrows
    @Override
    public void update(SecKillGoods before, SecKillGoods after) {
        // 索引导入
        secKillGoodsSearchFeign.add(JSON.parseObject(JSON.toJSONString(after), SecKillGoodsEs.class));

        // 生成/更新静态页
        secKillPageFeign.general(after.getId());
    }
}
