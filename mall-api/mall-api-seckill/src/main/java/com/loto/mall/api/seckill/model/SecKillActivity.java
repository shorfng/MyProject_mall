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
 * Date：2022-06-27 14:12<p>
 * PageName：SecKillActivity.java<p>
 * Function：
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "seckill_activity")   // MyBatisPlus表映射注解
public class SecKillActivity {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String activityName;
    private Integer type;   // 活动分类（0 shop秒杀、1每日特价、2大牌闪购、 3品类秒杀、4节日活动）
    private Date startTime;
    //@Column(name = "end_time")
    private Date endTime;
}
