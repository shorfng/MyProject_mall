package com.loto.mall.es.service;

import com.loto.mall.api.es.model.SkuEs;

import java.util.Map;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-17 20:07<p>
 * PageName：SkuEsService.java<p>
 * Function：
 */

public interface SkuEsService {
    /**
     * 搜索数据
     */
    Map<String, Object> search(Map<String, Object> searchMap);

    /**
     * 增加索引
     *
     * @param skuEs
     */
    void add(SkuEs skuEs);

    /**
     * 删除索引
     *
     * @param id
     */
    void del(String id);
}
