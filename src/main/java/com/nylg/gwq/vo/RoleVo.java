package com.nylg.gwq.vo;

import com.nylg.gwq.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class RoleVo extends Role {
    private static final long serialVersionUID=1L;
    private Integer page;
    private Integer limit;



}
