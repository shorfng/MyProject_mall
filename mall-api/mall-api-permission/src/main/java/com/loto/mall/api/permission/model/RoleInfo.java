package com.loto.mall.api.permission.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-30 20:05<p>
 * PageName：RoleInfo.java<p>
 * Function：
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "role_info")  // MyBatisPlus表映射注解
public class RoleInfo implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String roleName;
    private String description;

    // 权限列表
    @TableField(exist = false)
    private List<Permission> permissions;
}
