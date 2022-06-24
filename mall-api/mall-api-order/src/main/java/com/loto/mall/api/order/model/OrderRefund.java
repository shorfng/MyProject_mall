package com.loto.mall.api.order.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-24 18:09<p>
 * PageName：OrderRefund.java<p>
 * Function：
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "order_refund")  // MyBatisPlus表映射注解
public class OrderRefund implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String orderNo;
    private Integer refundType;
    private String orderSkuId;
    private String username;
    private Integer status;
    private Date createTime;
    private Integer money;
}
