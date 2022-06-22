package com.loto.mall.api.pay.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-22 18:52<p>
 * PageName：PayLog.java<p>
 * Function：
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "pay_log")  // MyBatisPlus表映射注解
public class PayLog {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    
    private Integer status;
    private String content;
    private String payId;
    private Date createTime;
}
