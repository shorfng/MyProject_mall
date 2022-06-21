package com.loto.mall.cart.service;

import com.loto.mall.api.cart.model.Cart;

import java.util.List;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-21 9:47<p>
 * PageName：ICartService.java<p>
 * Function：
 */

public interface ICartService {
    /**
     * 加入购物车
     * @param id
     * @param userName
     * @param num
     */
    void add(String id, String userName, Integer num);

    /**
     * 购物车列表
     * @param userName
     * @return
     */
    List<Cart> list(String userName);

    /**
     * 结算页面 - 根据购物车的ID集合查询购物车数据
     * @param ids
     * @return
     */
    List<Cart> list(List<String> ids);

    /**
     * 根据 ids 删除购物车数据
     * @param ids
     */
    void delete(List<String> ids);
}
