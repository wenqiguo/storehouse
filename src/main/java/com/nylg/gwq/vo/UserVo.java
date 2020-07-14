package com.nylg.gwq.vo;

import com.nylg.gwq.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class UserVo extends User {
    private static final long serialVersionUID=1L;
    private Integer page;
    private Integer limit;



}
