package com.nylg.gwq.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.nylg.gwq.entity.Loginfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
public class LoginfoVo extends Loginfo {
    private static final long serialVersionUID=1L;
    private Integer page;
    private Integer limit;
    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private Date endTime;


}
