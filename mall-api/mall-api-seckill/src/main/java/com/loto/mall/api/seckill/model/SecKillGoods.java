package com.loto.mall.api.seckill.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 14:14<p>
 * PageName：SecKillGoods.java<p>
 * Function：
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "seckill_goods")  // MyBatisPlus表映射注解
public class SecKillGoods {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String supId;
    private String skuId;
    private String name;
    private String images;
    private String content;
    private Integer price;
    private Integer seckillPrice;
    private Integer num;
    private Integer storeCount;
    private String activityId;

    // 商品锁定状态（0未锁定 1已锁定）
    private Integer isLock;
}
