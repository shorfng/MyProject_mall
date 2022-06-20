package com.loto.mall.canal.listener;

import com.alibaba.fastjson.JSON;
import com.loto.mall.api.search.feign.SkuSearchFeign;
import com.loto.mall.api.search.model.SkuSearch;
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
    private SkuSearchFeign skuSearchFeign;

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
            skuSearchFeign.add(JSON.parseObject(JSON.toJSONString(sku), SkuSearch.class));
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
            skuSearchFeign.del(after.getId());
        } else {
            // 更新（将Sku转成JSON，再将JSON转成SkuEs）
            skuSearchFeign.add(JSON.parseObject(JSON.toJSONString(after), SkuSearch.class));
        }
    }

    /**
     * 删除数据监听
     *
     * @param sku
     */
    @Override
    public void delete(Sku sku) {
        skuSearchFeign.del(sku.getId());
    }
}
