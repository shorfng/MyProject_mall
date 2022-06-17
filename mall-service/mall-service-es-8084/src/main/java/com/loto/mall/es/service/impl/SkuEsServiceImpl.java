package com.loto.mall.es.service.impl;

import com.alibaba.fastjson.JSON;
import com.loto.mall.es.mapper.SkuEsMapper;
import com.loto.mall.es.service.SkuEsService;
import com.loto.mall.api.es.model.SkuEs;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
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
     * 商品数据
     */
    @Override
    public Map<String, Object> search(Map<String, Object> searchMap) {
        // 构建搜索条件
        NativeSearchQueryBuilder queryBuilder = queryBuilder(searchMap);

        // 去 ES 搜索
        Page<SkuEs> page = skuEsMapper.search(queryBuilder.build());

        // 获取结果集：集合列表、总记录数
        Map<String, Object> resultMap = new HashMap<>();

        List<SkuEs> list = page.getContent();
        resultMap.put("list", list);

        return resultMap;
    }

    /**
     * 搜索条件构建
     *
     * @param searchMap
     * @return
     */
    public NativeSearchQueryBuilder queryBuilder(Map<String, Object> searchMap) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        // 判断关键词是否为空，不为空，则设置条件
        if (searchMap != null && searchMap.size() > 0) {
            // 关键词条件
            Object keywords = searchMap.get("keywords");
            if (!StringUtils.isEmpty(keywords)) {
                builder.withQuery(QueryBuilders.termQuery("name", keywords.toString()));
            }
        }

        return builder;
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
