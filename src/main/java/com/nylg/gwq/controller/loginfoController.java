package com.nylg.gwq.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nylg.gwq.common.DataGrideView;
import com.nylg.gwq.dao.LoginfoMapper;
import com.nylg.gwq.entity.Loginfo;
import com.nylg.gwq.service.LoginfoService;
import com.nylg.gwq.vo.LoginfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loginfo")
public class loginfoController {
    @Autowired
    private LoginfoService loginfoService;


    @RequestMapping("/loadAllLoginfo")
    public DataGrideView getAll(LoginfoVo loginfoVo){
        IPage<Loginfo> page = new Page<>(loginfoVo.getPage(),loginfoVo.getLimit());
        QueryWrapper<Loginfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNoneBlank(loginfoVo.getLoginname()),"loginname",loginfoVo.getLoginname());
        queryWrapper.like(StringUtils.isNoneBlank(loginfoVo.getLoginip()),"loginip",loginfoVo.getLoginip());
        queryWrapper.gt(loginfoVo.getStartTime()!=null,"logintime",loginfoVo.getStartTime());
        queryWrapper.le(loginfoVo.getEndTime()!=null,"logintime",loginfoVo.getEndTime());
        queryWrapper.orderByDesc("logintime");
        this.loginfoService.page(page,queryWrapper);
        return new DataGrideView(page.getTotal(),page.getRecords());

    }

}
