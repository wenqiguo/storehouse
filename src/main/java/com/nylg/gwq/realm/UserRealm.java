package com.nylg.gwq.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nylg.gwq.common.ActiverUser;
import com.nylg.gwq.entity.User;
import com.nylg.gwq.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public class UserRealm  extends AuthorizingRealm {

    @Autowired
    @Lazy //只有使用才加载
   private UserService userService;


    @Override
    public String getName(){
        return this.getClass().getSimpleName();
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
       /* QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("loginname",token.getPrincipal().toString());*/
        String name = token.getPrincipal().toString();
        User user = userService.getByName(name);
        if (user!=null){
            ActiverUser activerUser = new ActiverUser();
            activerUser.setUser(user);
            ByteSource credentialSalt = ByteSource.Util.bytes(user.getSalt());
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activerUser,user.getPwd(),credentialSalt,this.getName());
            return info;
        }

        return null;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

}
