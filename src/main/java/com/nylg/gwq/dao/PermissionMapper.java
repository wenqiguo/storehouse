package com.nylg.gwq.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.nylg.gwq.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

public interface PermissionMapper extends BaseMapper<Permission> {

    // //根据菜单表的id，删除菜单表的内容以及菜单和角色表的关系
    void deleteRoleByPid(@Param("id") Serializable id);
}
