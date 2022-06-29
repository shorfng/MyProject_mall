package com.loto.mall.api.druid.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-29 15:15<p>
 * PageName：HotGoods.java<p>
 * Function：
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "mslog")
public class HotGoods {
    //IP
    private String ip;

    //访问的uri
    private String uri;

    //时间
    @TableField("__time")
    private Date accessTime;
}
