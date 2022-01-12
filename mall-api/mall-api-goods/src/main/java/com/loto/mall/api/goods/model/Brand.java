package com.loto.mall.api.goods.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author：蓝田_Loto
 * <p>Date：2022-01-11 17:55</p>
 * <p>PageName：Brand.java</p>
 * <p>Function：品牌</p>
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "brand")
public class Brand {
    /**
     * 品牌 id（主键）
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 品牌名字
     */
    private String name;

    /**
     * 品牌图片
     */
    private String image;

    /**
     * 品牌首字母
     */
    private String initial;

    /**
     * 排序
     */
    private Integer sort;
}
