package com.loto.mall.api.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-30 19:17<p>
 * PageName：UserInfo.java<p>
 * Function：
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user_info") // MyBatisPlus表映射注解
public class UserInfo {
    @TableId(type = IdType.ASSIGN_ID)
    private String userName;

    private String password;
    private String phone;
    private String name;
    private Integer points;
    private String rolesId;
}
