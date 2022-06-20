package com.loto.mall.detail.service;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-20 17:56<p>
 * PageName：IDetailService.java<p>
 * Function：
 */

public interface IDetailService {
    /**
     * 生成商品详情的静态页
     * @param spuId
     */
    void html(String spuId) throws Exception;
}
