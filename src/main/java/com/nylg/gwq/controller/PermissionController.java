package com.nylg.gwq.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nylg.gwq.common.Constast;
import com.nylg.gwq.common.DataGrideView;
import com.nylg.gwq.common.Result;
import com.nylg.gwq.common.TreeNodes;
import com.nylg.gwq.entity.Permission;
import com.nylg.gwq.service.PermissionService;
import com.nylg.gwq.vo.PermissionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /*权限管理开始*/

    @RequestMapping("loadPermissionManagerLeftTreeJson")
    public DataGrideView loadPermissionManageLeftManage(PermissionVo vo){
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constast.TYPE_MENU);
        List<Permission> list = this.permissionService.list(queryWrapper);
        List<TreeNodes> treeNodes = new ArrayList<>();
        for (Permission permission: list){
            Boolean spread = permission.getOpen()==1?true:false;
            treeNodes.add(new TreeNodes(permission.getId(),permission.getPid(),permission.getTitle(),spread));
        }
        return new DataGrideView(treeNodes);
    }

    /**
     * 加载权限
     * @param vo
     * @return
     */

    @RequestMapping("/loadAllPermission")
    public DataGrideView loadAllPermission(PermissionVo vo){

        IPage<Permission> page = new Page<>(vo.getPage(),vo.getLimit());
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",Constast.TYPE_PERMISSION);//只查询权限
        queryWrapper.like(StringUtils.isNoneBlank(vo.getTitle()),"title",vo.getTitle());
        queryWrapper.eq(vo.getId()!=null,"pid",vo.getId());
        queryWrapper.orderByAsc("ordernum");
        this.permissionService.page(page, queryWrapper);
        return new DataGrideView(page.getTotal(),page.getRecords());

    }

    /**
     * 添加权限
     * @param vo
     * @return
     */
    @RequestMapping("/addPermission")
    public Result addPermission(PermissionVo vo){
        try {
            vo.setType(Constast.TYPE_PERMISSION);
            this.permissionService.save(vo);
            return Result.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADD_FAILE;
        }
    }
    /**
     * 加载最大排序码
     */
    @RequestMapping("loadPermissionMaxOrderNum")
    public Map<String,Object> loadPermissionMaxOrderNum(){
        Map<String,Object> map = new HashMap<>();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("ordernum");
        IPage<Permission> page = new Page<>(1,1);
        List<Permission> Permissions = this.permissionService.page(page,queryWrapper).getRecords();
        if (Permissions.size()>0){
            map.put("value",Permissions.get(0).getOrdernum()+1);
        }else {
            map.put("value",1);
        }
        return map;
    }

    /**
     * 修改部门
     * @param vo
     * @return
     */
    @RequestMapping("/updatePermission")
    public Result updatePermission(PermissionVo vo){
        try {
            this.permissionService.updateById(vo);
            return Result.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.UPDATE_FAILE;
        }
    }

    /**
     * 权限删除
     * @param permissionVo
     * @return
     */
    @RequestMapping("/deletePermission")
    public Result deletePermission(PermissionVo permissionVo) {
        try {
            this.permissionService.removeById(permissionVo.getId());
            return Result.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.DELETE_FAILE;
        }
    }


    /*权限管理结束*/
}
