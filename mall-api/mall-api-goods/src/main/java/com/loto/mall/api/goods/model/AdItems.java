package com.loto.mall.api.goods.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 蓝田_Loto
 * @since 2022-06-13 22:42:39
 */
@Getter
@Setter
@TableName("ad_items")
@ApiModel(value = "AdItems对象", description = "")
public class AdItems implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    @ApiModelProperty("分类，1首页推广,2列表页推广")
    private Integer type;

    @ApiModelProperty("展示的产品")
    private String skuId;

    @ApiModelProperty("排序")
    private Integer sort;
}
