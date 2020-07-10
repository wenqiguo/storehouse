package com.nylg.gwq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nylg.gwq.dao.PermissionMapper;
import com.nylg.gwq.entity.Permission;
import com.nylg.gwq.service.PermissionService;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
