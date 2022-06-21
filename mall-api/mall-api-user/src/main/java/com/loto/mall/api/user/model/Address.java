package com.loto.mall.api.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-21 12:32<p>
 * PageName：Address.java<p>
 * Function：
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "address")  // MyBatisPlus表映射注解
public class Address implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String userName;
    private String provinceId;
    private String cityId;
    private String areaId;
    private String phone;
    private String address;
    private String contact;
    private Integer isDefault;
}
