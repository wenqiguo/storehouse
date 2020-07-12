package com.nylg.gwq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nylg.gwq.dao.PermissionMapper;
import com.nylg.gwq.entity.Permission;
import com.nylg.gwq.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {


    @Override
    public boolean removeById(Serializable id) {
        PermissionMapper permissionMapper = this.getBaseMapper();
        //根据菜单表的id，删除菜单表的内容以及菜单和角色表的关系
        permissionMapper.deleteRoleByPid(id);

        return super.removeById(id);
    }
}
