package com.nylg.gwq.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nylg.gwq.dao.UserMapper;
import com.nylg.gwq.entity.User;
import com.nylg.gwq.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
