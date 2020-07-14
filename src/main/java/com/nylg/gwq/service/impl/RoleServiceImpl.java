package com.nylg.gwq.service.impl;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nylg.gwq.dao.RoleMapper;
import com.nylg.gwq.entity.Role;
import com.nylg.gwq.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public boolean removeById(Serializable id) {

        this.getBaseMapper().deleteById(id);
        //删除角色与菜单对应关系sys_role_permission
        this.getBaseMapper().deleteRolePermissonByRid(id);
        //删除角色和用户关联表 sys_role_user
        this.getBaseMapper().deleteRoleUserByRid(id);
        //删除一个角色
        return super.removeById(id);
    }

    @Override
    public List<Integer> queryRolePermissionIdByRid(Integer roleId) {
        return this.getBaseMapper().queryRolePermissionIdByRid(roleId);
    }

    /**
     * 保存角色和权限之间的关系
     * @param rid
     * @param ids
     */
    @Override
    public void savePermissionIdAndRoleId(Integer rid, Integer[] ids) {
        RoleMapper roleMapper = this.getBaseMapper();
        roleMapper.deleteRolePermissonByRid(rid);
        if (ids!=null&&ids.length>0){
            for (Integer id :ids){
                roleMapper.savePermissionIdAndRoleId(rid,id);
            }
        }
    }

    /**
     * 查询用户拥有的角色合集
     * @param id
     * @return
     */

    @Override
    public List<Integer> queryRoleIdsByUid(Integer id) {
        return this.baseMapper.queryRoleIdsByUid(id);
    }

    @Override
    public void saveUserRole(Integer uid, Integer[] ids) {
        //先删除该用户原有的角色和用户的关系
        this.baseMapper.deleteRoleUserByUid(uid);
        if (ids.length>0&&ids!=null){
            for (Integer rid : ids){
                this.baseMapper.saveUidAndRid(uid,rid);
            }
        }

        //保存新的角色用户关系
    }
}
