package com.nylg.gwq.controller;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nylg.gwq.common.DataGrideView;
import com.nylg.gwq.common.Result;
import com.nylg.gwq.common.TreeNodes;
import com.nylg.gwq.entity.Dept;
import com.nylg.gwq.service.DeptService;
import com.nylg.gwq.vo.DeptVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dept")
public class deptController {
    @Autowired
    private DeptService deptService;


    @RequestMapping("loadDeptManagerLeftTreeJson")
    public DataGrideView loadDeptManageLeftManage(DeptVo deptVo){
        List<Dept> list = this.deptService.list();
        List<TreeNodes> treeNodes = new ArrayList<>();
        for (Dept dept: list){
           /* TreeNodes nodes = new TreeNodes();
            BeanUtils.copyProperties(dept,nodes);
            treeNodes.add(nodes);*/
            Boolean spread = dept.getOpen()==1?true:false;
            treeNodes.add(new TreeNodes(dept.getId(),dept.getPid(),dept.getTitle(),spread));
        }
        return new DataGrideView(treeNodes);
    }

    @RequestMapping("/loadAllDept")
    public DataGrideView LoadAllDept(DeptVo vo){

        IPage<Dept> page = new Page<>(vo.getPage(),vo.getLimit());
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNoneBlank(vo.getTitle()),"title",vo.getTitle());
        queryWrapper.like(StringUtils.isNoneBlank(vo.getAddress()),"address",vo.getAddress());
        queryWrapper.like(StringUtils.isNoneBlank(vo.getRemark()),"remark",vo.getRemark());
        queryWrapper.eq(vo.getId()!=null,"id",vo.getId()).or().eq(vo.getId()!=null,"pid",vo.getId());
        queryWrapper.orderByAsc("ordernum");
        this.deptService.page(page, queryWrapper);
        return new DataGrideView(page.getTotal(),page.getRecords());

    }

    /**
     * 添加部门
     * @param vo
     * @return
     */
    @RequestMapping("/addDept")
    public Result addDept(DeptVo vo){
        try {
            vo.setCreatetime(new Date());
            this.deptService.save(vo);
            return Result.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADD_FAILE;
        }
    }
    /**
     * 加载最大排序码
     */
    @RequestMapping("loadDeptMaxOrderNum")
    public Map<String,Object> loadDeptMaxOrderNum(){
        Map<String,Object> map = new HashMap<>();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("ordernum");
        List<Dept> depts = this.deptService.list(queryWrapper);
        if (depts.size()>0){
            map.put("ordernum",depts.get(0).getOrdernum()+1);
        }else {
            map.put("ordernum",1);
        }
        return map;
    }

    /**
     * 添加部门
     * @param vo
     * @return
     */
    @RequestMapping("/updateDept")
    public Result updateDept(DeptVo vo){
        try {
            vo.setCreatetime(new Date());
            this.deptService.updateById(vo);
            return Result.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.UPDATE_FAILE;
        }
    }
  //删除前判断是否有子节点
    @RequestMapping("/checkDeptHasChildrenNode")
    public Map<String,Object> checkDeptHasChildrenNode(DeptVo vo){
        Map<String,Object> map = new HashMap<>();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("pid",vo.getId());
        List<Dept> depts = this.deptService.list(queryWrapper);
        if (depts.size()>0){
            map.put("value",true);
        }else{
            map.put("value",false);
        }
        return map;
    }

    /**
     * 部门删除
     * @param id
     * @return
     */
    @RequestMapping("/deleteDept")
    public Result deleteDept(Integer id) {
        if (id==null){
           return Result.DELETE_FAILE;
        }
       boolean b = deptService.removeById(id);
        if (b){
            return Result.DELETE_SUCCESS;
        }else {
            return Result.DELETE_FAILE;
        }
    }
}
