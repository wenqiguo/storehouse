package com.nylg.gwq.common;


//状态码
public class Constast {
    /**
     * 状态码
     */
    public static final Integer OK = 200;
    public static final Integer ERROR = -1;

    /**
     * 用户默认密码
     */
    public static final String USER_DEFAULT_PWD= "123456";

    /**
     * 权限菜单类型
     */
    public static final String TYPE_MENU = "menu";

    public static final String TYPE_PERMISSION = "permission";

    /**
     * 可用状态
     */
    public static final Integer AVAILABLE_TRUE = 1;
    public static final Integer AVAILABLE_FALSE = 0;

    /**
     * 用户类型
     */
    public static final Integer USER_TYPER_SUPER = 0;
    public static final Integer USER_TYPER_NORMAL = 1;

    /**
     * 导航树的展开类型
     *
     */
    public static final Integer OPEN_TRUE = 1;
    public static final Integer OPEN_FALSE =0;




    private Integer code;



    Constast(Integer code) {
        this.code = code;
    }
}
