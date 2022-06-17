package com.loto.mall.api.goods.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商品表（每个产品信息）
 * </p>
 *
 * @author 蓝田_Loto
 * @since 2022-06-13 22:42:39
 */
@Data
@Table
@ApiModel(value = "Sku对象", description = "商品表（每个产品信息）")
public class Sku implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品id")
    private String id;

    @ApiModelProperty("SKU名称")
    private String name;

    @ApiModelProperty("价格（分）")
    private Integer price;

    @ApiModelProperty("库存数量")
    private Integer num;

    @ApiModelProperty("商品图片")
    private String image;

    @ApiModelProperty("商品图片列表")
    private String images;

    @ApiModelProperty("创建时间")
    @Column(name = "create_time")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @Column(name = "update_time")
    private Date updateTime;

    @ApiModelProperty("spuId")
    @Column(name = "spu_id")
    private String spuId;

    @ApiModelProperty("类目ID")
    @Column(name = "category_id")
    private Integer categoryId;

    @ApiModelProperty("类目名称")
    @Column(name = "category_name")
    private String categoryName;

    @ApiModelProperty("品牌id")
    @Column(name = "brand_id")
    private Integer brandId;

    @ApiModelProperty("品牌名称")
    @Column(name = "brand_name")
    private String brandName;

    @ApiModelProperty("规格")
    @Column(name = "sku_attribute")
    private String skuAttribute;

    @ApiModelProperty("商品状态 1-正常，2-下架，3-删除")
    private Integer status;
}
