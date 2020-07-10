package com.nylg.gwq.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    public static Result LOGIN_SUCCESS = new Result(Constast.OK,"登录成功");
    public static Result LOGIN_FILE = new Result(Constast.ERROR,"登录失败,用户名或者密码不正确");
    public static Result LOGIN_PICTURE_WRONG= new Result(Constast.ERROR,"登录失败,验证码错误");



    public static Result DELETE_SUCCESS = new Result(Constast.OK,"删除成功");
    public static Result DELETE_FAILE = new Result(Constast.ERROR,"删除失败");

    public static Result UPDATE_SUCCESS = new Result(Constast.OK,"修改成功");
    public static Result UPDATE_FAILE = new Result(Constast.ERROR,"修改失败");

    public static Result ADD_SUCCESS = new Result(Constast.OK,"添加成功");
    public static Result ADD_FAILE = new Result(Constast.ERROR,"添加失败");

    public static Result RESET_SUCCESS = new Result(Constast.OK,"重置成功");
    public static Result DRESET_FAILE = new Result(Constast.ERROR,"重置失败");

    public static Result DISPATCH_SUCCESS = new Result(Constast.OK,"分发成功");
    public static Result DISPATCH_FAILE = new Result(Constast.ERROR,"分发失败");
    private Integer code;
    private String msg;

}
