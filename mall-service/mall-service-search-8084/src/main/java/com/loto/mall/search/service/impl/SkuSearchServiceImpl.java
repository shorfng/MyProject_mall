package com.loto.mall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.loto.mall.api.search.model.SkuSearch;
import com.loto.mall.search.mapper.SkuSearchMapper;
import com.loto.mall.search.service.SkuSearchService;
import com.loto.mall.search.util.HighlightResultMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-17 20:09<p>
 * PageName：SkuSearchServiceImpl.java<p>
 * Function：
 */

@Service
public class SkuSearchServiceImpl implements SkuSearchService {
    @Autowired
    private SkuSearchMapper skuSearchMapper;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 增加索引
     *
     * @param skuSearch
     */
    @Override
    public void add(SkuSearch skuSearch) {
        // 获取属性
        String attrMap = skuSearch.getSkuAttribute();

        // 不为空，则将属性添加到 attrMap 中
        if (!StringUtils.isEmpty(attrMap)) {
            skuSearch.setAttrMap(JSON.parseObject(attrMap, Map.class));
        }

        skuSearchMapper.save(skuSearch);
    }

    /**
     * 删除索引
     *
     * @param id
     */
    @Override
    public void del(String id) {
        skuSearchMapper.deleteById(id);
    }

    /**
     * 商品数据
     */
    @Override
    public Map<String, Object> search(Map<String, Object> searchMap) {
        // 构建搜索条件
        NativeSearchQueryBuilder queryBuilder = queryBuilder(searchMap);

        // 分组搜索
        group(queryBuilder, searchMap);

        // 设置高亮信息：关键词前（后）面的标签、设置高亮域
        HighlightBuilder.Field field = new HighlightBuilder
                .Field("name")                     // 根据指定的域进行高亮查询
                .preTags("<span style=\"color:red;\">")  // 关键词高亮前缀
                .postTags("</span>")    // 高亮关键词后缀
                .fragmentSize(100);     // 碎片长度
        queryBuilder.withHighlightFields(field);

        // 将非高亮数据替换成高亮数据

        // 去 ES 搜索
        //Page<SkuEs> page = skuEsMapper.search(queryBuilder.build());
        //AggregatedPage<SkuEs> page = (AggregatedPage<SkuEs>) skuEsMapper.search(queryBuilder.build());
        AggregatedPage<SkuSearch> page = elasticsearchRestTemplate.queryForPage(
                queryBuilder.build(),
                SkuSearch.class,
                new HighlightResultMapper()  // 映射转换
        );

        // 获取结果集：集合列表、总记录数
        Map<String, Object> resultMap = new HashMap<>();

        // 解析：分组数据
        parseGroup(page.getAggregations(), resultMap);

        // 解析：动态属性（将属性信息合并成Map对象）
        attrParse(resultMap);

        List<SkuSearch> list = page.getContent();
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

        // 组合查询对象
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // 判断关键词是否为空，不为空，则设置条件
        if (searchMap != null && searchMap.size() > 0) {
            // 关键词条件
            Object keywords = searchMap.get("keywords");
            if (!StringUtils.isEmpty(keywords)) {
                //builder.withQuery(QueryBuilders.termQuery("name", keywords.toString()));
                boolQueryBuilder.must(QueryBuilders.termQuery("name", keywords.toString()));
            }

            // 分类查询
            Object category = searchMap.get("category");
            if (!StringUtils.isEmpty(category)) {
                boolQueryBuilder.must(QueryBuilders.termQuery("categoryName", category.toString()));
            }

            // 品牌查询
            Object brand = searchMap.get("brand");
            if (!StringUtils.isEmpty(brand)) {
                boolQueryBuilder.must(QueryBuilders.termQuery("brandName", brand.toString()));
            }

            // 价格区间查询（price=0-500元  500-1000元  1000元以上）
            Object price = searchMap.get("price");
            if (!StringUtils.isEmpty(price)) {
                // 区间 [x,y] 的极值
                String[] prices = price.toString().replace("元", "").replace("以上", "").split("-");

                // price>x
                boolQueryBuilder.must(QueryBuilders.rangeQuery("price").gt(Integer.valueOf(prices[0])));

                // price<=y（1000元以上，prices.length == 1）
                if (prices.length == 2) {
                    boolQueryBuilder.must(QueryBuilders.rangeQuery("price").lte(Integer.valueOf(prices[1])));
                }
            }

            // 动态属性查询
            for (Map.Entry<String, Object> entry : searchMap.entrySet()) {
                // 以 attr_ 开始，就是动态属性（比如：attr_网络：移动5G）
                if (entry.getKey().startsWith("attr_")) {
                    String key = "attrMap." + entry.getKey().replaceFirst("attr_", "") + ".keyword";
                    boolQueryBuilder.must(QueryBuilders.termQuery(key, entry.getValue().toString()));
                }
            }

            // 排序
            Object orderField = searchMap.get("orderField");
            Object orderType = searchMap.get("orderType");
            if (!StringUtils.isEmpty(orderField) && !StringUtils.isEmpty(orderType)) {
                builder.withSort(
                        SortBuilders.fieldSort(orderField.toString())                // 指定排序域
                                    .order(SortOrder.valueOf(orderType.toString()))  // 排序方式
                );
            }
        }

        // 分页查询
        builder.withPageable(PageRequest.of(currentPage(searchMap), 5));
        return builder.withQuery(boolQueryBuilder);
    }

    /**
     * 分组查询
     */
    public void group(NativeSearchQueryBuilder queryBuilder, Map<String, Object> searchMap) {
        // 分类分组查询（用户如果没有输入分类条件，则需要将分类搜索出来，作为条件提供给用户）
        if (StringUtils.isEmpty(searchMap.get("category"))) {
            queryBuilder.addAggregation(
                    AggregationBuilders
                            .terms("categoryList")   // 别名，类似Map的key
                            .field("categoryName")         // 根据 categoryName 域进行分组
                            .size(100)                     // 分组结果显示100个
            );
        }

        // 品牌分组查询（用户如果没有输入品牌条件，则需要将品牌搜索出来，作为条件提供给用户）
        if (StringUtils.isEmpty(searchMap.get("brand"))) {
            queryBuilder.addAggregation(
                    AggregationBuilders
                            .terms("brandList")  // 别名，类似Map的key
                            .field("brandName")        // 根据 brandName 域进行分组
                            .size(100)                 // 分组结果显示100个
            );
        }

        // 属性分组查询
        if (StringUtils.isEmpty(searchMap.get("attrMap"))) {
            queryBuilder.addAggregation(
                    AggregationBuilders
                            .terms("attrMaps")   // 别名，类似Map的key
                            .field("skuAttribute")     // 根据 skuAttribute 域进行分组
                            .size(100000)              // 分组结果显示100000个
            );
        }
    }

    /**
     * 解析：分组结果
     */
    public void parseGroup(Aggregations aggregations, Map<String, Object> resultMap) {
        if (aggregations != null) {
            for (Aggregation aggregation : aggregations) {
                // 强转成 ParsedStringTerms
                ParsedStringTerms terms = (ParsedStringTerms) aggregation;

                // 循环结果集对象
                List<String> values = new ArrayList<>();
                for (Terms.Bucket bucket : terms.getBuckets()) {
                    values.add(bucket.getKeyAsString());
                }

                // 名字
                String key = aggregation.getName();

                // 组合数据
                resultMap.put(key, values);
            }
        }
    }

    /**
     * 解析：动态属性（将属性信息合并成Map对象）
     */
    public void attrParse(Map<String, Object> searchMap) {
        // 获取 attrMaps
        Object attrMaps = searchMap.get("attrMaps");

        if (attrMaps != null) {
            // 定义一个集合 Map<String,Set<String>> 存储所有汇总数据
            Map<String, Set<String>> allMaps = new HashMap<>();

            // 集合数据
            List<String> groupList = (List<String>) attrMaps;

            // 循环集合
            for (String attr : groupList) {
                Map<String, String> attrMap = JSON.parseObject(attr, Map.class);
                for (Map.Entry<String, String> entry : attrMap.entrySet()) {
                    // 获取每条记录，将记录转成 Map
                    String key = entry.getKey();
                    Set<String> values = allMaps.get(key);
                    if (values == null) {
                        values = new HashSet<>();
                    }

                    values.add(entry.getValue());

                    // 覆盖之前的数据
                    allMaps.put(key, values);
                }
            }

            // 覆盖之前的 attrMaps
            searchMap.put("attrMaps", allMaps);
        }
    }

    /**
     * 分页参数
     */
    public int currentPage(Map<String, Object> searchMap) {
        try {
            Object page = searchMap.get("page");
            return Integer.parseInt(page.toString()) - 1;
        } catch (Exception e) {
            return 0;
        }
    }
}
