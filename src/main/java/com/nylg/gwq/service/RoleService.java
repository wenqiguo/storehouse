package com.nylg.gwq.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.nylg.gwq.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {

    // 从role_permission中获取菜单权限id合集
    List<Integer> queryRolePermissionIdByRid(Integer roleId);

    void savePermissionIdAndRoleId(Integer roleId, Integer[] ids);

    List<Integer> queryRoleIdsByUid(Integer id);

    void saveUserRole(Integer uid, Integer[] ids);
}
