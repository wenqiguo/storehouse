package com.nylg.gwq.controller;

import com.nylg.gwq.common.ActiverUser;
import com.nylg.gwq.common.Result;
import com.nylg.gwq.entity.Loginfo;
import com.nylg.gwq.entity.User;
import com.nylg.gwq.service.LoginfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;


@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginfoService loginfoService;

    @RequestMapping("/login")
    public Result login(String loginname,String pwd,HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken(loginname,pwd);
        try {
            subject.login(token);
            ActiverUser user = (ActiverUser)subject.getPrincipal();
            HttpSession session = request.getSession();
            User u = user.getUser();
            //记录登录日志
            Loginfo loginfo = new Loginfo();
            loginfo.setLoginip(request.getRemoteAddr()); //获取ip
            loginfo.setLoginname(u.getLoginname());
            loginfo.setLogintime(new Date());
            loginfoService.save(loginfo);
           session.setAttribute("user",u);
            return Result.LOGIN_SUCCESS;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return Result.LOGIN_FILE;
        }


    }
}
