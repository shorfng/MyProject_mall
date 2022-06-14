package com.loto.mall.api.goods.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 品牌表
 * </p>
 *
 * @author 蓝田_Loto
 * @since 2022-06-13 22:42:39
 */
@Getter
@Setter
@ApiModel(value = "Brand对象", description = "品牌表")
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("品牌名称")
    private String name;

    @ApiModelProperty("品牌图片地址")
    private String image;

    @ApiModelProperty("品牌的首字母")
    private String initial;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("分类")
    @TableField(exist = false)
    private List<Category> categories;
}
