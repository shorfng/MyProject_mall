package com.loto.mall.permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.loto.mall.api.permission.model.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-30 22:09<p>
 * PageName：PermissionMapper.java<p>
 * Function：
 */

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 查询所有角色的权限映射关系
     * @return
     */
    @Select("SELECT * FROM role_permission")
    List<Map<Integer, Integer>> allRolePermissions();
}
