package com.nylg.gwq.vo;

import com.nylg.gwq.entity.Dept;
import lombok.Data;
import lombok.EqualsAndHashCode;



@Data
@EqualsAndHashCode(callSuper = false)
public class DeptVo extends Dept {
    private static final long serialVersionUID=1L;
    private Integer page;
    private Integer limit;



}
