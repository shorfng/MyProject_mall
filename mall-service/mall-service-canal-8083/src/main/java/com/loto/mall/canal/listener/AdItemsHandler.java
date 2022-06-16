package com.loto.mall.canal.listener;

import com.loto.mall.api.goods.feign.SkuFeign;
import com.loto.mall.api.goods.model.AdItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-17 1:00<p>
 * PageName：AdItemsHandler.java<p>
 * Function：
 */

@Component
@CanalTable(value = "ad_items")   // canal 监听 ad_items 表
public class AdItemsHandler implements EntryHandler<AdItems> {
    @Autowired
    private SkuFeign skuFeign;

    /**
     * 数据库增加数据，执行该方法
     *
     * @param adItems
     */
    @Override
    public void insert(AdItems adItems) {
        System.out.println("增加");
        // 重新加载缓存
        skuFeign.updateTypeItems(adItems.getType());
    }

    /**
     * 数据库修改数据，执行该方法
     */
    @Override
    public void update(AdItems before, AdItems after) {
        System.out.println("修改");
        if (before.getType().intValue() != after.getType().intValue()) {
            // 重新加载变更前分类的ID对应的推广产品
            skuFeign.updateTypeItems(before.getType());
        }

        // 重新加载缓存
        skuFeign.updateTypeItems(after.getType());
    }

    /**
     * 数据库删除数据，执行该方法
     */
    @Override
    public void delete(AdItems adItems) {
        System.out.println("删除");
        skuFeign.delTypeItems(adItems.getType());
    }
}
