package com.loto.mall.api.goods.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
@ApiModel(value = "Sku对象", description = "商品表（每个产品信息）")
public class Sku implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("SPUID")
    private String spuId;

    @ApiModelProperty("类目ID")
    private Integer categoryId;

    @ApiModelProperty("类目名称")
    private String categoryName;

    @ApiModelProperty("品牌id")
    private Integer brandId;

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("规格")
    private String skuAttribute;

    @ApiModelProperty("商品状态 1-正常，2-下架，3-删除")
    private Integer status;


}
