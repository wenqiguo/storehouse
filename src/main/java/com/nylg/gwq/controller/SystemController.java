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



}
