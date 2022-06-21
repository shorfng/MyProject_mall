package com.loto.mall.api.order.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-21 16:14<p>
 * PageName：OrderSku.java<p>
 * Function：
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "order_sku")  // MyBatisPlus表映射注解
public class OrderSku {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String image;
    private String skuId;
    private String orderId;
    private String name;
    private Integer price;
    private Integer num;    // 数量
    private Integer money;  // 总金额
}
