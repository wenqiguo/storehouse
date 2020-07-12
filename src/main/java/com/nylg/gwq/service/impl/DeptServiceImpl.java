package com.nylg.gwq.service.impl;

import java.io.Serializable;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nylg.gwq.dao.DeptMapper;
import com.nylg.gwq.entity.Dept;
import com.nylg.gwq.service.DeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {
	
	@Override
	public Dept getOne(Wrapper<Dept> queryWrapper) {
		return super.getOne(queryWrapper);
	}
	
	@Override
	public boolean updateById(Dept entity) {
		// TODO Auto-generated method stub
		return super.updateById(entity);
	}


	@Override
	public boolean removeById(Serializable id) {
		return super.removeById(id);
	}

}
