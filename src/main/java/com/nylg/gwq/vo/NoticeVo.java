package com.nylg.gwq.vo;

import com.nylg.gwq.entity.Notice;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
public class NoticeVo extends Notice {
    private static final long serialVersionUID=1L;
    private Integer page;
    private Integer limit;
    private Integer[] ids;  //方便批量删除
    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private Date endTime;


}
