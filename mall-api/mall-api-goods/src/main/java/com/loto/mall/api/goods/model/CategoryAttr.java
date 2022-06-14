package com.loto.mall.api.goods.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 分类属性表
 * </p>
 *
 * @author 蓝田_Loto
 * @since 2022-06-13 22:42:39
 */
@Getter
@Setter
@TableName("category_attr")
@ApiModel(value = "CategoryAttr对象", description = "分类属性表")
public class CategoryAttr implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分类id")
    private Integer categoryId;

    @ApiModelProperty("属性id")
    private Integer attrId;
}
