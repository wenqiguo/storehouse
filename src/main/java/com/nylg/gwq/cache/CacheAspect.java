package com.nylg.gwq.cache;

import com.nylg.gwq.entity.Dept;
import com.nylg.gwq.entity.User;
import com.nylg.gwq.vo.DeptVo;
import com.nylg.gwq.vo.UserVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@EnableAspectJAutoProxy
public class CacheAspect {
    //声明一个缓存容器
    private Map<String, Object> CACHE_CONTAINER = new HashMap<>();


    /**
     * 日志处理
     */
    private Log log = LogFactory.getLog(CacheAspect.class);


    //声明切面表达式
    private static final String POINTCUT_DEPT_UPDATE = "execution(* com.nylg.gwq.service.impl.DeptServiceImpl.updateById(..))";
    private static final String POINTCUT_DEPT_ADD = "execution(* com.nylg.gwq.service.impl.DeptServiceImpl.save(..))";
    private static final String POINTCUT_DEPT_GET = "execution(* com.nylg.gwq.service.impl.DeptServiceImpl.getById(..))";
    private static final String POINTCUT_DEPT_DELETE = "execution(* com.nylg.gwq.service.impl.DeptServiceImpl.removeById(..))";

    private static final String CACHE_DEPT_PROFIX = "dept:";


    /**
     * 添加切入
     */
    @Around(value = POINTCUT_DEPT_ADD)
    public Object cacheDeptAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        Dept dept = (Dept) joinPoint.getArgs()[0];
        Boolean proceed = (Boolean) joinPoint.proceed();
        if (proceed){
            CACHE_CONTAINER.put(CACHE_DEPT_PROFIX+dept.getId(),dept);
        }
        return proceed;
    }

    /**
     * 查询切入
     */
    @Around(value = POINTCUT_DEPT_GET)
    public Object cacheDeptGet(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出参数
        Integer object = (Integer) joinPoint.getArgs()[0];
        //从缓存中取
        Object res1 = CACHE_CONTAINER.get(CACHE_DEPT_PROFIX + object);
        if (res1 != null) {
            log.info("已从缓存中找到部门对象"+CACHE_DEPT_PROFIX + object);
            return res1;
        } else {
            log.info("未从缓存中找到部门对象，从数据库取出"+CACHE_DEPT_PROFIX + object);
            Dept res2 = (Dept) joinPoint.proceed(); //获取执行方法的返回值
            CACHE_CONTAINER.put(CACHE_DEPT_PROFIX + res2.getId(), res2);
            return res2;
        }
    }


    /**
     * 更新切入
     */
    @Around(value = POINTCUT_DEPT_UPDATE)
    public Object cacheDeptUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        DeptVo deptVo = (DeptVo) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            Dept dept = (Dept) CACHE_CONTAINER.get(CACHE_DEPT_PROFIX + deptVo.getId());
            if (dept==null){
                log.info("部门对象，缓存已更新"+CACHE_DEPT_PROFIX + deptVo.getId());
                BeanUtils.copyProperties(dept,dept);
                CACHE_CONTAINER.put(CACHE_DEPT_PROFIX + deptVo.getId(),dept);
            }
        }
        return isSuccess;
    }

    /**
     *删除切入
     */
    @Around(value = POINTCUT_DEPT_DELETE)
    public Object cacheDeptDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess){
            CACHE_CONTAINER.remove(CACHE_DEPT_PROFIX+id);
            log.info("部门对象，缓存已更新"+CACHE_DEPT_PROFIX + id);
        }
        return isSuccess;
    }

    //声明切面表达式
    private static final String POINTCUT_USER_UPDATE = "execution(* com.nylg.gwq.service.impl.UserServiceImpl.updateById(..))";
    private static final String POINTCUT_USER_SAVE = "execution(* com.nylg.gwq.service.impl.UserServiceImpl.save(..))";
    private static final String POINTCUT_USER_GET = "execution(* com.nylg.gwq.service.impl.UserServiceImpl.getById(..))";
    private static final String POINTCUT_USER_DELETE = "execution(* com.nylg.gwq.service.impl.UserServiceImpl.removeById(..))";

    private static final String CACHE_USER_PROFIX = "user:";

    /**
     * 添加切入
     */
    @Around(value = POINTCUT_USER_SAVE)
    public Object cacheUserAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        User user = (User) joinPoint.getArgs()[0];
        Boolean proceed = (Boolean) joinPoint.proceed();
        if (proceed){
            CACHE_CONTAINER.put(CACHE_DEPT_PROFIX+user.getId(),user);
        }
        return proceed;
    }

    /**
     * 查询切入
     */
    @Around(value = POINTCUT_USER_GET)
    public Object cacheUserGet(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出参数
        Integer object = (Integer) joinPoint.getArgs()[0];
        //从缓存中取
        Object res1 = CACHE_CONTAINER.get(CACHE_USER_PROFIX + object);
        if (res1 != null) {
            log.info("已从缓存中找到用户对象"+CACHE_USER_PROFIX + object);
            return res1;
        } else {
            log.info("未从缓存中找到用户对象，从数据库取出"+CACHE_USER_PROFIX + object);
            User res2 = (User) joinPoint.proceed(); //获取执行方法的返回值
            CACHE_CONTAINER.put(CACHE_USER_PROFIX + res2.getId(), res2);
            return res2;
        }
    }


    /**
     * 更新切入
     */
    @Around(value = POINTCUT_USER_UPDATE)
    public Object cacheUserUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
       User userVo = (User) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            User user = (User) CACHE_CONTAINER.get(CACHE_USER_PROFIX + userVo.getId());
            if (user==null){
                user = new User();
            }
            log.info("用户对象，缓存已更新"+CACHE_USER_PROFIX + userVo.getId());
            BeanUtils.copyProperties(userVo,user);
            CACHE_CONTAINER.put(CACHE_USER_PROFIX + userVo.getId(),user);
        }
        return isSuccess;
    }

    /**
     *删除切入
     */
    @Around(value = POINTCUT_USER_DELETE)
    public Object cacheUserDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess){
            CACHE_CONTAINER.remove(CACHE_USER_PROFIX+id);
            log.info("部门对象，缓存已更新"+CACHE_USER_PROFIX + id);
        }
        return isSuccess;
    }
}


