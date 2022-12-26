package com.loto.mall.api.goods.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 商品表（同类产品信息）
 * </p>
 *
 * @author 蓝田_Loto
 * @since 2022-06-13 22:42:39
 */
@Getter
@Setter
@ApiModel(value = "Spu对象", description = "商品表（同类产品信息）")
public class Spu implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("SPU名")
    private String name;

    @ApiModelProperty("简介")
    private String intro;

    @ApiModelProperty("品牌ID")
    private Integer brandId;

    @ApiModelProperty("一级分类")
    private Integer categoryOneId;

    @ApiModelProperty("二级分类")
    private Integer categoryTwoId;

    @ApiModelProperty("三级分类")
    private Integer categoryThreeId;

    @ApiModelProperty("图片列表")
    private String images;

    @ApiModelProperty("售后服务")
    private String afterSalesService;

    @ApiModelProperty("介绍")
    private String content;

    @ApiModelProperty("规格列表")
    private String attributeList;

    @ApiModelProperty("是否上架,0已下架，1已上架")
    private Integer isMarketable;

    @ApiModelProperty("是否删除,0:未删除，1：已删除")
    private Integer isDelete;

    @ApiModelProperty("审核状态，0：未审核，1：已审核，2：审核不通过")
    private Integer status;
}
