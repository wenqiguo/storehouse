package com.nylg.gwq.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nylg.gwq.dao.RoleMapper;
import com.nylg.gwq.dao.UserMapper;
import com.nylg.gwq.entity.User;
import com.nylg.gwq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;


@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public boolean save(User entity) {
        return super.save(entity);
    }

    @Override
    public boolean updateById(User entity) {
        return super.updateById(entity);
    }

    @Override
    public User getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean removeById(Serializable id) {
        //根据用户id删除用户角色中间表的数据
        roleMapper.deleteRoleUserByUid(id);
        //删除用户头像，如果是默认头像不删除

        return super.removeById(id);
    }

    @Override
    public User getByName(String name) {
        return this.baseMapper.getByName(name);
    }
}
