package com.loto.mall.permission.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loto.mall.api.permission.model.Permission;

import java.util.List;
import java.util.Map;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-30 22:12<p>
 * PageName：PermissionService.java<p>
 * Function：
 */

public interface PermissionService extends IService<Permission> {
    /**
     * 根据不同匹配方式查询权限列表
     * @param matchMethod
     * @return
     */
    List<Permission> findByMatch(Integer matchMethod);

    /**
     * 查询所有角色的权限映射关系
     * @return
     */
    List<Map<Integer, Integer>> allRolePermissions();
}
