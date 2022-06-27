package com.loto.mall.api.seckill.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 14:16<p>
 * PageName：SecKillOrder.java<p>
 * Function：
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "seckill_order")  // MyBatisPlus表映射注解
public class SecKillOrder {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String seckillGoodsId;
    private String weixinTransactionId;
    private String username;
    private Integer money;
    private Integer status;
    private Date createTime;
    private Date payTime;
    private Integer num;    // 抢购数量
}
