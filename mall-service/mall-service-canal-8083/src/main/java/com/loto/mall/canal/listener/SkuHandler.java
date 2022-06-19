package com.loto.mall.canal.listener;

import com.alibaba.fastjson.JSON;
import com.loto.mall.api.search.feign.SkuEsFeign;
import com.loto.mall.api.search.model.SkuEs;
import com.loto.mall.api.goods.model.Sku;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-17 20:35<p>
 * PageName：SkuHandler.java<p>
 * Function：
 */

@CanalTable(value = "sku")
@Component
public class SkuHandler implements EntryHandler<Sku> {
    @Autowired
    private SkuEsFeign skuEsFeign;

    /**
     * 增加数据监听
     *
     * @param sku
     */
    @SneakyThrows
    @Override
    public void insert(Sku sku) {
        if (sku.getStatus() == 1) {
            // 新增（将Sku转成JSON，再将JSON转成SkuEs）
            skuEsFeign.add(JSON.parseObject(JSON.toJSONString(sku), SkuEs.class));
        }
    }

    /**
     * 修改数据监听
     *
     * @param before
     * @param after
     */
    @SneakyThrows
    @Override
    public void update(Sku before, Sku after) {
        if (after.getStatus() == 2) {
            // 删除索引
            skuEsFeign.del(after.getId());
        } else {
            // 更新（将Sku转成JSON，再将JSON转成SkuEs）
            skuEsFeign.add(JSON.parseObject(JSON.toJSONString(after), SkuEs.class));
        }
    }

    /**
     * 删除数据监听
     *
     * @param sku
     */
    @Override
    public void delete(Sku sku) {
        skuEsFeign.del(sku.getId());
    }
}
