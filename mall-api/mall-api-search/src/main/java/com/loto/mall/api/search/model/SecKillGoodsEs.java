package com.loto.mall.api.search.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 16:09<p>
 * PageName：SecKillGoodsEs.java<p>
 * Function：秒杀商品同步ES索引库实体类
 */

@Data
@Document(indexName = "mall-seckill-goods",type = "mall")
public class SecKillGoodsEs {
    @Id
    private String id;

    private String supId;
    private String skuId;

    @Field(type= FieldType.Text,searchAnalyzer = "ik_smart",analyzer = "ik_smart")
    private String name;

    private String images;
    private Integer price;
    private Integer seckillPrice;
    private Integer num;
    private Integer storeCount;

    @Field(type=FieldType.Keyword)
    private String activityId;
}
