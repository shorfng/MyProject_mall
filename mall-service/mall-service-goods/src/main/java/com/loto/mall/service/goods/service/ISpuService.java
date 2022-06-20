package com.loto.mall.service.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loto.mall.api.goods.model.Product;
import com.loto.mall.api.goods.model.Spu;
/**
 * <p>
 * 商品表（同类产品信息） 服务类
 * </p>
 *
 * @author 蓝田_Loto
 * @since 2022-06-13 22:42:39
 */
public interface ISpuService extends IService<Spu> {
    /**
     * 保存同类产品信息
     * @param product
     */
    void saveProduct(Product product);

    /**
     * 根据 spuId 查询商品信息
     * @param id
     * @return
     */
    Product findBySupId(String id);
}
