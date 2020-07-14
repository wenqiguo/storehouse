package com.nylg.gwq.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nylg.gwq.common.ActiverUser;
import com.nylg.gwq.common.Constast;
import com.nylg.gwq.entity.Permission;
import com.nylg.gwq.entity.User;
import com.nylg.gwq.service.PermissionService;
import com.nylg.gwq.service.RoleService;
import com.nylg.gwq.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRealm  extends AuthorizingRealm {

    @Autowired
    @Lazy //只有使用才加载
   private UserService userService;

    @Autowired
    @Lazy
    private PermissionService permissionService;

    @Autowired
    @Lazy
    private RoleService roleService;




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
            //根据用户id查询percode权限
            QueryWrapper<Permission> qw = new QueryWrapper<>();
            qw.eq("type", Constast.TYPE_PERMISSION);
            qw.eq("available", Constast.AVAILABLE_TRUE);
            //根据用户获取角色+权限
            Integer uid = user.getId();
            //获取角色id
            List<Integer> currentRoleIds = roleService.queryRoleIdsByUid(uid);
            //通过角色获取菜单和权限
            Set<Integer> pids = new HashSet<>();
            for (Integer  id : currentRoleIds){
                List<Integer> pidList = roleService.queryRolePermissionIdByRid(id);
                pids.addAll(pidList);
            }
            List<Permission> permissions = new ArrayList<>();
            //根据pid查Perssion
            if (pids.size()>0) {
                qw.in("pid", pids);
                permissions = permissionService.list(qw);
            }
            List<String> perCodes = new ArrayList<>();
            for (Permission p :permissions){
                perCodes.add(p.getPercode());
            }
            activerUser.setPermissions(perCodes);
            ByteSource credentialSalt = ByteSource.Util.bytes(user.getSalt());
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activerUser,user.getPwd(),credentialSalt,this.getName());
            return info;
        }

        return null;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        ActiverUser activerUser = (ActiverUser) principalCollection.getPrimaryPrincipal();
        User user = activerUser.getUser();
        List<String> permission = activerUser.getPermissions();
        if (user.getType().equals(Constast.USER_TYPER_SUPER)){
            simpleAuthorizationInfo.addStringPermission("*:*");
        }else{
            if (permission!=null&&permission.size()>0){
                simpleAuthorizationInfo.addStringPermissions(permission);
            }
        }

        return simpleAuthorizationInfo;
    }

}
