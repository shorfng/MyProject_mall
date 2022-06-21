package com.loto.mall.cart.service;

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
}
