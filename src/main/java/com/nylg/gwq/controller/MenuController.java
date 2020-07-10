package com.nylg.gwq.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nylg.gwq.common.Constast;
import com.nylg.gwq.common.DataGrideView;
import com.nylg.gwq.common.TreeNodeBuilder;
import com.nylg.gwq.common.TreeNodes;
import com.nylg.gwq.entity.Permission;
import com.nylg.gwq.entity.User;
import com.nylg.gwq.service.PermissionService;
import com.nylg.gwq.vo.PermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

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
}
