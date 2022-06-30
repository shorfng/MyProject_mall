package com.loto.mall.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loto.mall.api.permission.model.Permission;
import com.loto.mall.permission.mapper.PermissionMapper;
import com.loto.mall.permission.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-30 22:12<p>
 * PageName：PermissionServiceImpl.java<p>
 * Function：
 */

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 根据不同匹配方式查询权限列表
     * @param matchMethod
     * @return
     */
    @Override
    public List<Permission> findByMatch(Integer matchMethod) {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url_match", matchMethod);
        return permissionMapper.selectList(queryWrapper);
    }

    /**
     * 查询所有角色的权限映射关系
     * @return
     */
    @Override
    public List<Map<Integer, Integer>> allRolePermissions() {
        return permissionMapper.allRolePermissions();
    }
}
