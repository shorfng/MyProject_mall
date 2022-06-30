package com.loto.mall.api.permission.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-30 20:05<p>
 * PageName：Permission.java<p>
 * Function：
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "permission") // MyBatisPlus表映射注解
public class Permission implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String sourceName;
    private String url;
    private Integer urlMatch;
    private String serviceName;
    private String method;
}
