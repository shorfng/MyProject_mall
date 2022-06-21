package com.loto.mall.api.cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-21 9:32<p>
 * PageName：Cart.java<p>
 * Function：
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart implements Serializable {
    @Id
    private String _id;       // 主键

    private String userName;  // 用户名字
    private String name;      // 商品名字
    private Integer price;    // 商品单价
    private String image;     // 商品图片
    private String skuId;     // 商品ID
    private Integer num;      // 商品数量
}
