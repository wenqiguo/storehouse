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
    private Integer code;
    private String msg;

}
