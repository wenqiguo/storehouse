package com.nylg.gwq.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nylg.gwq.dao.LoginfoMapper;
import com.nylg.gwq.entity.Loginfo;
import com.nylg.gwq.service.LoginfoService;
import org.springframework.stereotype.Service;


@Service
public class LoginfoServiceImpl extends ServiceImpl<LoginfoMapper, Loginfo> implements LoginfoService {

}
