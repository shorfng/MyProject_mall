package com.loto.mall.cart.service.impl;

import com.loto.mall.api.cart.model.Cart;
import com.loto.mall.api.goods.feign.SkuFeign;
import com.loto.mall.api.goods.model.Sku;
import com.loto.mall.cart.mapper.CartMapper;
import com.loto.mall.cart.service.ICartService;
import com.loto.mall.util.common.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-21 9:47<p>
 * PageName：CartServiceImpl.java<p>
 * Function：
 */

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private SkuFeign skuFeign;

    /**
     * 加入购物车
     *
     * @param id
     * @param userName
     * @param num      当前商品加入购物车总数量
     */
    @Override
    public void add(String id, String userName, Integer num) {
        // 删除旧的 用户名 + 当前商品ID 在 MongoDB 中的购物车记录
        cartMapper.deleteById(userName + id);

        if (num > 0) {
            // 根据ID查询Sku商品详情
            RespResult<Sku> skuResp = skuFeign.one(id);

            // 将当前ID商品对应的数据加入购物车（存入到MongoDB）
            Sku sku = skuResp.getData();
            Cart cart = new Cart(userName + id, userName, sku.getName(), sku.getPrice(), sku.getImage(), id, num);
            cartMapper.save(cart);
        }
    }
}
