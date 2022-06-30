package com.loto.mall.permission.Init;

import com.loto.mall.api.permission.model.Permission;
import com.loto.mall.permission.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-30 22:14<p>
 * PageName：InitPermission.java<p>
 * Function：
 */

@Component
public class InitPermission implements ApplicationRunner {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RedisTemplate redisTemplate;

    //@Autowired
    //private RedissonClient redissonClient;

    /**
     * 权限初始化加载
     *
     * @param args incoming application arguments
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 加载所有权限
        List<Permission> permissionMatch0 = permissionService.findByMatch(0);  // 0 完全匹配过滤
        List<Permission> permissionMatch1 = permissionService.findByMatch(1);  // 1 通配符匹配

        // 所有角色的权限 查询角色权限映射关系
        List<Map<Integer, Integer>> rolePermissions = permissionService.allRolePermissions();

        // 匹配每个角色拥有的权限列表
        Map<String, Set<Permission>> roleMap = rolePermissionFilter(rolePermissions, permissionMatch0, permissionMatch1);

        // 数据存入 Redis 缓存
        redisTemplate.boundHashOps("RolePermissionAll").put("PermissionMatch0", permissionMatch0);
        redisTemplate.boundHashOps("RolePermissionAll").put("PermissionMatch1", permissionMatch1);

        // 所有角色权限
        redisTemplate.boundHashOps("RolePermissionMap").putAll(roleMap);

        // 存储权限判断部分uri到布隆过滤器中 - 完全匹配
        //RBloomFilter<String> filters = redissonClient.getBloomFilter("UriBloomFilterArray");
        //filters.tryInit(1000000L, 0.0001);
        //for (Permission permission : permissionMatch0) {
        //    filters.add(permission.getUrl());
        //}
    }

    /**
     * 每个角色拥有的权限
     */
    public Map<String, Set<Permission>> rolePermissionFilter(List<Map<Integer, Integer>> rolePermissions,
                                                             List<Permission> permissionMatch0,
                                                             List<Permission> permissionMatch1) {
        // 每个角色拥有的权限，存入到 map 中
        // Match 0、Match 1
        Map<String, Set<Permission>> rolePermissionMapping = new HashMap<>();

        // 循环所有的角色关系映射
        for (Map<Integer, Integer> rolePermissionMap : rolePermissions) {
            Integer rid = rolePermissionMap.get("rid");  // 角色ID
            Integer pid = rolePermissionMap.get("pid");  // 权限ID

            // 定义 Key
            String key0 = "Role_0_" + rid;
            String key1 = "Role_1_" + rid;

            // 获取当前角色的权限列表
            Set<Permission> permissionsSet0 = rolePermissionMapping.get(key0);
            Set<Permission> permissionsSet1 = rolePermissionMapping.get(key1);

            permissionsSet0 = permissionsSet0 == null ? new HashSet<>() : permissionsSet0;
            permissionsSet1 = permissionsSet1 == null ? new HashSet<>() : permissionsSet1;

            // 查找每个角色对应的权限 - 完全匹配
            for (Permission permission : permissionMatch0) {
                if (permission.getId().intValue() == pid.intValue()) {
                    // 权限匹配完成
                    permissionsSet0.add(permission);
                    break;
                }
            }

            if (permissionsSet0.size() > 0) {
                rolePermissionMapping.put(key0, permissionsSet0);
            }

            // 查找每个角色对应的权限 - 通配符匹配
            for (Permission permission : permissionMatch1) {
                if (permission.getId().intValue() == pid.intValue()) {
                    // 权限匹配完成
                    permissionsSet1.add(permission);
                    break;
                }
            }

            if (permissionsSet1.size() > 0) {
                rolePermissionMapping.put(key1, permissionsSet1);
            }
        }

        return rolePermissionMapping;
    }
}
