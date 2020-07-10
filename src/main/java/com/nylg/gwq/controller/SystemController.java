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
}
