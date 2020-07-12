package com.nylg.gwq.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nylg.gwq.common.*;
import com.nylg.gwq.entity.Permission;
import com.nylg.gwq.entity.User;
import com.nylg.gwq.service.PermissionService;
import com.nylg.gwq.vo.PermissionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/loadIndexLeftMenuJson")
    public DataGrideView loadIndexLeftMenuJson(PermissionVo vo, HttpSession session){
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constast.TYPE_MENU);
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        List<Permission> list = null;
        User user = (User)session.getAttribute("user");
        if (user.getType()==Constast.USER_TYPER_SUPER){
            list =permissionService.list();
        }else {
            //根据用户获取角色+权限
            list =permissionService.list();
        }
        List<TreeNodes> treeNodes = new ArrayList<>();
        for (Permission p : list){
            Integer id = p.getId();
            Integer pid =p.getPid();
            String title = p.getTitle();
            String icon = p.getIcon();
            String href = p.getHref();
            Boolean spread = p.getOpen()== Constast.OPEN_TRUE ? true:false;
           treeNodes.add(new TreeNodes(id,pid,title,icon,href,spread));
        }
        //构造层级关系
        List<TreeNodes> list2 = TreeNodeBuilder.build(treeNodes,1);
        return new DataGrideView(list2);
    }

    /*菜单管理开始*/

    @RequestMapping("loadMenuManagerLeftTreeJson")
    public DataGrideView loadMenuManageLeftManage(PermissionVo vo){
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",Constast.TYPE_MENU);
        List<Permission> list = this.permissionService.list(queryWrapper);
        List<TreeNodes> treeNodes = new ArrayList<>();
        for (Permission permission: list){
            Boolean spread = permission.getOpen()==1?true:false;
            treeNodes.add(new TreeNodes(permission.getId(),permission.getPid(),permission.getTitle(),spread));
        }
        return new DataGrideView(treeNodes);
    }

    /**
     * 加载菜单
     * @param vo
     * @return
     */

    @RequestMapping("/loadAllMenu")
    public DataGrideView loadAllMenu(PermissionVo vo){

        IPage<Permission> page = new Page<>(vo.getPage(),vo.getLimit());
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(vo.getId()!=null,"id",vo.getId()).or().eq(vo.getId()!=null,"pid",vo.getId());
        queryWrapper.eq("type",Constast.TYPE_MENU);//只查询菜单
        queryWrapper.like(StringUtils.isNoneBlank(vo.getTitle()),"title",vo.getTitle());
        queryWrapper.orderByAsc("ordernum");
        this.permissionService.page(page, queryWrapper);
        return new DataGrideView(page.getTotal(),page.getRecords());

    }

    /**
     * 添加菜单
     * @param vo
     * @return
     */
    @RequestMapping("/addMenu")
    public Result addMenu(PermissionVo vo){
        try {
            vo.setType(Constast.TYPE_MENU);
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
    @RequestMapping("loadMenuMaxOrderNum")
    public Map<String,Object> loadMenuMaxOrderNum(){
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
     * 修改菜单
     * @param vo
     * @return
     */
    @RequestMapping("/updateMenu")
    public Result updateMenu(PermissionVo vo){
        try {
            this.permissionService.updateById(vo);
            return Result.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.UPDATE_FAILE;
        }
    }
    //删除前判断是否有子节点
    @RequestMapping("/checkMenuHasChildrenNode")
    public Map<String,Object> checkMenuHasChildrenNode(PermissionVo vo){
        Map<String,Object> map = new HashMap<>();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("pid",vo.getId());
        List<Permission> Permissions = this.permissionService.list(queryWrapper);
        if (Permissions.size()>0){
            map.put("value",true);
        }else{
            map.put("value",false);
        }
        return map;
    }

    /**
     * 菜单删除
     * @param id
     * @return
     */
    @RequestMapping("/deleteMenu")
    public Result deleteMenu(Integer id) {
        if (id == null) {
            return Result.DELETE_FAILE;
        }
        boolean b = permissionService.removeById(id);
        if (b) {
            return Result.DELETE_SUCCESS;
        } else {
            return Result.DELETE_FAILE;
        }
    }

    
    /*菜单管理结束*/
}
