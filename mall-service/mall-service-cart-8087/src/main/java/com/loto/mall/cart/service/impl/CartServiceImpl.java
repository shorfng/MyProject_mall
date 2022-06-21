package com.loto.mall.cart.service.impl;

import com.google.common.collect.Lists;
import com.loto.mall.api.cart.model.Cart;
import com.loto.mall.api.goods.feign.SkuFeign;
import com.loto.mall.api.goods.model.Sku;
import com.loto.mall.cart.mapper.CartMapper;
import com.loto.mall.cart.service.ICartService;
import com.loto.mall.util.common.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private MongoTemplate mongoTemplate;

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

    /**
     * 购物车列表
     *
     * @param userName
     * @return
     */
    @Override
    public List<Cart> list(String userName) {
        // 条件构建
        Cart cart = new Cart();
        cart.setUserName(userName);

        return cartMapper.findAll(Example.of(cart), Sort.by("_id"));
    }

    /**
     * 结算页面 - 根据购物车的ID集合查询购物车数据
     *
     * @param ids
     * @return
     */
    @Override
    public List<Cart> list(List<String> ids) {
        if (ids != null && ids.size() > 0) {
            // 根据ID集合查询
            Iterable<Cart> carts = cartMapper.findAllById(ids);
            return Lists.newArrayList(carts);
        }

        return null;
    }

    /**
     * 根据 ids 删除购物车数据
     *
     * @param ids
     */
    @Override
    public void delete(List<String> ids) {
        // _id in(ids)
        mongoTemplate.remove(Query.query(Criteria.where("_id").in(ids)), Cart.class);
    }
}
