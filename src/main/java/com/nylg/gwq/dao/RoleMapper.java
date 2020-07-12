package com.nylg.gwq.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nylg.gwq.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;


public interface RoleMapper extends BaseMapper<Role> {

    //删除角色与菜单对应关系sys_role_permission
    void deleteRolePermissonByRid(@Param("id") Serializable id);

    //删除角色和用户关联表 sys_role_user
    void deleteRoleUserByRid(@Param("id") Serializable id);

    // 从role_permission中获取菜单权限id合集
    List<Integer> queryRolePermissionIdByRid(Integer roleId);

    void savePermissionIdAndRoleId(@Param("rid") Integer rid,@Param("pid") Integer pid);
}
