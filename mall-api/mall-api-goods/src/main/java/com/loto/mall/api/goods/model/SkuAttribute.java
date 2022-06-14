package com.loto.mall.api.goods.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 属性表
 * </p>
 *
 * @author 蓝田_Loto
 * @since 2022-06-13 22:42:39
 */
@Getter
@Setter
@TableName("sku_attribute")
@ApiModel(value = "SkuAttribute对象", description = "属性表")
public class SkuAttribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("属性名称")
    private String name;

    @ApiModelProperty("属性选项")
    private String options;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("属性对应的分类")
    @TableField(exist = false)
    private List<Category> categories;
}
