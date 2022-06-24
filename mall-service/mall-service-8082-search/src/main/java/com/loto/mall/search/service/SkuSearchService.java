package com.loto.mall.search.service;

import com.loto.mall.api.search.model.SkuSearch;

import java.util.Map;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-17 20:07<p>
 * PageName：SkuSearchService.java<p>
 * Function：
 */

public interface SkuSearchService {
    /**
     * 增加索引
     *
     * @param skuSearch
     */
    void add(SkuSearch skuSearch);

    /**
     * 删除索引
     *
     * @param id
     */
    void del(String id);

    /**
     * 商品搜索
     */
    Map<String, Object> search(Map<String, Object> searchMap);

}
