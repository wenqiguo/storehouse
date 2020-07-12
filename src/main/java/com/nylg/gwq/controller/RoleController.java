package com.nylg.gwq.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nylg.gwq.common.Constast;
import com.nylg.gwq.common.DataGrideView;
import com.nylg.gwq.common.Result;
import com.nylg.gwq.common.TreeNodes;
import com.nylg.gwq.entity.Permission;
import com.nylg.gwq.entity.Role;
import com.nylg.gwq.service.PermissionService;
import com.nylg.gwq.service.RoleService;
import com.nylg.gwq.vo.RoleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 保存用户角色
     */
    @RequestMapping("saveRolePermission")
    public Result saveRolePermission(Integer rid,Integer[] ids){

        try {


            this.roleService.savePermissionIdAndRoleId(rid,ids);
            return Result.DISPATCH_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return Result.DISPATCH_FAILE;
        }
    }

    /**
     * 查询角色id查询菜单和树所拥有json串
     * @param
     * @return
     */
    @RequestMapping("initPermissionByRoleId")
    public DataGrideView initPermissionByRoleId(Integer roleId){
            //查询所有可用的菜单和权限
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        List<Permission> allPermission = this.permissionService.list(queryWrapper);
            //查询当前角色拥有的菜单和权限
        // 从role_permission中获取菜单权限id合集
        List<Integer> pids = this.roleService.queryRolePermissionIdByRid(roleId);
        List<Permission> currentPermission = null;
        if (pids.size()>0){
            queryWrapper.in("id",pids);
            // 在permission中取出权限和菜单
            currentPermission= this.permissionService.list(queryWrapper);
        }else {
            currentPermission = new ArrayList<>();
        }

        //构造TreeNodes的返回值
        List<TreeNodes> treeNodes = new ArrayList<>();
        for (Permission per : allPermission){
            String checkArr = "0";
            for (Permission now : currentPermission){
                if (per.getId()==now.getId()){
                    checkArr = "1";
                }
            }
            Boolean spread = (per.getOpen()==null || per.getOpen()==1)?true:false;
            treeNodes.add(new TreeNodes(per.getId(),per.getPid(),per.getTitle(),spread,checkArr));
        }
        return new DataGrideView(treeNodes);
    }



    @RequestMapping("/loadAllRole")
    public DataGrideView LoadAllRole(RoleVo vo){

        IPage<Role> page = new Page<>(vo.getPage(),vo.getLimit());
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNoneBlank(vo.getName()),"name",vo.getName());
        queryWrapper.like(StringUtils.isNoneBlank(vo.getRemark()),"remark",vo.getRemark());
        queryWrapper.eq(vo.getAvailable()!=null,"available",vo.getAvailable());
        queryWrapper.orderByDesc("createtime");
        this.roleService.page(page, queryWrapper);
        return new DataGrideView(page.getTotal(),page.getRecords());

    }

    /**
     * 添加
     * @param
     * @return
     */
    @RequestMapping("/addRole")
    public Result addRole(RoleVo RoleVo,HttpSession session){
        try {
             RoleVo.setCreatetime(new Date());
            this.roleService.save(RoleVo);
            return Result.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADD_FAILE;
        }
    }
    /**
     * 修改公告
     * @param
     * @return
     */
    @RequestMapping("/updateRole")
    public Result addRole(RoleVo RoleVo){
        try {
            this.roleService.updateById(RoleVo);
            return Result.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.UPDATE_FAILE;
        }
    }

    /**
     * 单条删除
     * @param id
     * @return
     */
    @RequestMapping("/deleteRole")
    public Result deleteRole(Integer id){
        try {
            this.roleService.removeById(id);
            return Result.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.DELETE_FAILE;
        }

    }



}
