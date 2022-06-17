package com.loto.mall.es.service.impl;

import com.alibaba.fastjson.JSON;
import com.loto.mall.es.mapper.SkuEsMapper;
import com.loto.mall.es.service.SkuEsService;
import com.loto.mall.api.es.model.SkuEs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-17 20:09<p>
 * PageName：SkuEsServiceImpl.java<p>
 * Function：
 */

@Service
public class SkuEsServiceImpl implements SkuEsService {
    @Autowired
    private SkuEsMapper skuEsMapper;

    /**
     * 搜索数据
     */
    @Override
    public Map<String, Object> search(Map<String, Object> searchMap) {
        return null;
    }

    /**
     * 增加索引
     *
     * @param skuEs
     */
    @Override
    public void add(SkuEs skuEs) {
        // 获取属性
        String attrMap = skuEs.getSkuAttribute();

        // 不为空，则将属性添加到 attrMap 中
        if (!StringUtils.isEmpty(attrMap)) {
            skuEs.setAttrMap(JSON.parseObject(attrMap, Map.class));
        }

        skuEsMapper.save(skuEs);
    }

    /**
     * 删除索引
     *
     * @param id
     */
    @Override
    public void del(String id) {
        skuEsMapper.deleteById(id);
    }
}
