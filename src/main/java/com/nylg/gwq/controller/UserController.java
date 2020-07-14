package com.nylg.gwq.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nylg.gwq.common.Constast;
import com.nylg.gwq.common.DataGrideView;
import com.nylg.gwq.common.PinyinUtils;
import com.nylg.gwq.common.Result;
import com.nylg.gwq.entity.Dept;
import com.nylg.gwq.entity.Role;
import com.nylg.gwq.entity.User;
import com.nylg.gwq.service.DeptService;
import com.nylg.gwq.service.RoleService;
import com.nylg.gwq.service.UserService;
import com.nylg.gwq.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private RoleService roleService;

    /**
     * 保存给用户分配的角色
     * @param
     * @return
     */
    @RequestMapping("/saveUserRole")
    public Result saveUserRole(Integer uid,Integer[] ids){
        //sys_user中保存角色和用户逇对应关系
        //应该先删除用户的原有角色用户关系，添加新的
        try {
            roleService.saveUserRole(uid,ids);
            return Result.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.UPDATE_FAILE;
        }

    }


    /*
    * 根据用户id查询角色，并选中已经拥有角色
    * */

    @RequestMapping("/initRoleByUserId")
    public DataGrideView initRoleByUserId(Integer id){
        //查询可用的用户角色
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available",Constast.AVAILABLE_TRUE);
        List<Map<String, Object>> maps = this.roleService.listMaps(queryWrapper);
        //查询用户拥有的角色
        List<Integer> currendUserRoleIds = this.roleService.queryRoleIdsByUid(id);
        for (Map<String,Object> map:maps){
            Boolean LAY_CHECKED= false;
            Integer rid = (Integer) map.get("id");
            for (Integer roleId : currendUserRoleIds){
                if (rid==roleId){
                    LAY_CHECKED = true;
                    break;
                }
            }
            map.put("LAY_CHECKED",LAY_CHECKED);
        }
        return new DataGrideView(Long.valueOf(maps.size()),maps);


    }


    /**
     * 重置密码
     * @param
     * @return
     */
    @RequestMapping("/resetPwd")
    public Result resetPwd(Integer id){
         User user = new User();
        try {
            String salt = IdUtil.simpleUUID().toUpperCase();
            user.setId(id);
            user.setSalt(salt);
            user.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD,salt,2).toString());
            this.userService.updateById(user);
            return Result.UPDATE_SUCCESS;
        }catch (Exception e){
            return Result.UPDATE_FAILE;
        }
    }


    /**
     * 删除用户
     * @param userVo
     * @return
     */
    @RequestMapping("/deleteUser")
    public Result deleteUser(UserVo userVo){
        try {
            //删除用户前判断用户是否是其他用户的部门领导，如果是给提示
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq(userVo.getId()!=null,"pid",userVo.getId());
            List<User> users = this.deptService.list(queryWrapper);
            if (users!=null &&users.size()>0){
                return new Result(Constast.ERROR,"该部门领导有下属，不可删除");
            }
            this.userService.removeById(userVo.getId());
            return Result.DELETE_SUCCESS;
        }catch (Exception e){
            return Result.DELETE_FAILE;
        }
    }



    /**
     * 修改用户
     * @param userVo
     * @return
     */
    @RequestMapping("/updateUser")
    public Result updateUser(UserVo userVo){
        try {
            this.userService.updateById(userVo);
            return Result.UPDATE_SUCCESS;
        }catch (Exception e){
            return Result.UPDATE_FAILE;
        }
    }

    /**
     * 根据用户id查询用户
     * @param
     * @return
     */
    @RequestMapping("loadUserById")
    public DataGrideView loadUserById(Integer id){
        return new DataGrideView(userService.getById(id));
    }


    /**
     * 添加用户
     * @param userVo
     * @return
     */
    @RequestMapping("/addUser")
    public Result addUser(UserVo userVo){
        try {
            userVo.setType(Constast.USER_TYPER_NORMAL);
            userVo.setHiredate(new Date());
            String salt = IdUtil.simpleUUID().toUpperCase();
            userVo.setSalt(salt);
            userVo.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD,salt,2).toString());
            this.userService.save(userVo);
          return Result.ADD_SUCCESS;
        }catch (Exception e){
            return Result.ADD_FAILE;
        }
    }

    /**
     * 添加时把用户名转换为拼音，设置为登录名
     * @param username
     * @return
     */

    @RequestMapping("/changeChineseToPinyin")
    public Map<String,Object> changeChineseToPinyin(String username){
        Map<String,Object> map = new HashMap<>();
        String name = PinyinUtils.getPingYin(username);
        map.put("value",name);
        return map;


    }


    /**
     * 通过部门id，获取部门直属领导
     * @param deptid
     * @return
     */

    @RequestMapping("/loadUsersByDeptId")
    public DataGrideView loadUsersByDeptId(Integer deptid){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(deptid!=null,"deptid",deptid);
        queryWrapper.eq("available",Constast.AVAILABLE_TRUE);
        queryWrapper.eq("type",Constast.USER_TYPER_NORMAL);
        List<User> list = userService.list(queryWrapper);
        return new DataGrideView(list);
    }




    /**
     * 加载最大排序码
     */
    @RequestMapping("loadUserMaxOrderNum")
    public Map<String,Object> loadUsertMaxOrderNum(){
        Map<String,Object> map = new HashMap<>();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("ordernum");
        IPage<User> page = new Page<>(1,1);
        List<User> users = this.userService.page(page,queryWrapper).getRecords();
        if (users.size()>0){
            map.put("value",users.get(0).getOrdernum()+1);
        }else {
            map.put("value",1);
        }
        return map;
    }

    /**
     * 用户全查询
     */
    @RequestMapping("/loadAllUser")
    public DataGrideView loadAllUser(UserVo userVo){
        IPage<User> page = new Page<>(userVo.getPage(),userVo.getLimit());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNoneBlank(userVo.getName()),"name",userVo.getName()).or()
                .eq(StringUtils.isNoneBlank(userVo.getName()),"loginname",userVo.getName());
        queryWrapper.eq(StringUtils.isNotBlank(userVo.getAddress()), "address", userVo.getAddress());
        queryWrapper.eq("type", Constast.USER_TYPER_NORMAL);//查询普通用户
        queryWrapper.eq(userVo.getDeptid()!=null,"deptid",userVo.getDeptid());
        List<User> userList = this.userService.page(page,queryWrapper).getRecords();
        for (User u : userList){
            Integer deptid = u.getDeptid();
            if (deptid!=null){
                Dept one = deptService.getById(deptid);
                u.setDeptname(one.getTitle());
            }
            Integer mgr = u.getMgr();
            if (mgr!=null){
                User mgUser = userService.getById(mgr);
                u.setLeadername(mgUser.getName());
            }
        }
        return new DataGrideView(page.getTotal(),userList);
    }
}
