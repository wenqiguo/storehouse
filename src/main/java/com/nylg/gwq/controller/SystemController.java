package com.nylg.gwq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sys")
public class SystemController {

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "system/index/login";
    }

    @RequestMapping("/index")
    public String index(){
        return "system/index/index";
    }

    @RequestMapping("/toDeskManager")
    public String deskManager(){
        return "system/index/deskManager";
    }

    @RequestMapping("/toLogLoginManager")
    public String toLoginfoManager(){
        return "system/loginfo/loginManager";
    }

    @RequestMapping("/toNoticeManager")
    public String toNoticeManager(){
        return "system/notice/noticeManager";
    }

    /**
     * 部门管理主页面
     * @return
     */
    @RequestMapping("/toDeptManager")
    public String toDeptManager(){
        return "system/dept/deptManager";
    }

    /**
     * 跳转到部门管理左边
     * @return
     */

    @RequestMapping("/toDeptLeft")
    public String toDeptLeft(){
        return "system/dept/deptLeft";
    }

    /**
     * 跳转到部门管理右边
     * @return
     */
    @RequestMapping("/toDeptRight")
    public String toDeptRight(){
        return "system/dept/deptRight";
    }

    /**
     * 菜单管理主页面
     * @return
     */
    @RequestMapping("/toMenuManager")
    public String toMenuManager(){
        return "system/menu/menuManager";
    }

    /**
     * 跳转到菜单管理左边
     * @return
     */

    @RequestMapping("/toMenuLeft")
    public String toMenuLeft(){
        return "system/menu/menuLeft";
    }

    /**
     * 跳转到菜单管理右边
     * @return
     */
    @RequestMapping("/toMenuRight")
    public String toMenuRight(){
        return "system/menu/menuRight";
    }


    /**
     * 权限管理主页面
     * @return
     */
    @RequestMapping("/toPermissionManager")
    public String toPermissionManager(){
        return "system/permission/permissionManager";
    }

    /**
     * 跳转到权限管理左边
     * @return
     */

    @RequestMapping("/toPermissionLeft")
    public String toPermissionLeft(){
        return "system/permission/permissionLeft";
    }

    /**
     * 跳转到权限管理右边
     * @return
     */
    @RequestMapping("/toPermissionRight")
    public String toPermissionRight(){
        return "system/permission/permissionRight";
    }

    /**
     * 跳转到角色管理
     * @return
     */
    @RequestMapping("/toRoleManager")
    public String toRoleManager(){
        return "system/role/roleManager";
    }
}
