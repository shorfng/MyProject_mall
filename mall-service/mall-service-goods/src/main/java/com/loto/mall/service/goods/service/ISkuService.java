package com.loto.mall.service.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loto.mall.api.goods.model.Sku;

import java.util.List;

/**
 * <p>
 * 商品表（每个产品信息） 服务类
 * </p>
 *
 * @author 蓝田_Loto
 * @since 2022-06-13 22:42:39
 */
public interface ISkuService extends IService<Sku> {
    /**
     * 根据商品推广分类id，查询指定分类下的产品列表
     *
     * @param id
     * @return
     */
    List<Sku> typeSkuItems(Integer typeId);

    /**
     * 删除缓存（根据商品推广分类id，查询指定分类下的产品列表）
     *
     * @param typeId
     */
    void delTypeItems(Integer typeId);

    /**
     * 修改缓存（根据商品推广分类id，查询指定分类下的产品列表）
     *
     * @param typeId
     */
    List<Sku> updateTypeItems(Integer typeId);
}
