package com.loto.mall.api.search.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.Map;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-17 19:49<p>
 * PageName：SkuSearch.java<p>
 * Function：
 */

@Data
@Document(indexName = "mall-goods-sku", type = "mall")
public class SkuSearch {
    @Id
    private String id;

    // FieldType.Text：使用 ik 分词器
    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String name;

    private Integer price;
    private Integer num;
    private String image;
    private String images;
    private Date createTime;
    private Date updateTime;
    private String spuId;
    private Integer categoryId;

    // FieldType.Keyword：不分词
    @Field(type = FieldType.Keyword)
    private String categoryName;

    private Integer brandId;

    // FieldType.Keyword：不分词
    @Field(type = FieldType.Keyword)
    private String brandName;

    // FieldType.Keyword：不分词
    @Field(type = FieldType.Keyword)
    private String skuAttribute;

    private Integer status;

    // 属性映射（动态创建域信息）
    // key=就业薪资
    // value=1万
    // attrMap.就业薪资.keyword=1万
    private Map<String, String> attrMap;
}
