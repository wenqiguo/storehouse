package com.nylg.gwq.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nylg.gwq.common.DataGrideView;
import com.nylg.gwq.common.Result;
import com.nylg.gwq.entity.Notice;
import com.nylg.gwq.entity.User;
import com.nylg.gwq.service.NoticeService;
import com.nylg.gwq.vo.NoticeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @RequestMapping("/loadAllNotice")
    public DataGrideView LoadAllNotice(NoticeVo vo){

        IPage<Notice> page = new Page<>(vo.getPage(),vo.getLimit());
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNoneBlank(vo.getTitle()),"title",vo.getTitle());
        queryWrapper.like(StringUtils.isNoneBlank(vo.getOpername()),"opername",vo.getOpername());
        queryWrapper.ge(vo.getStartTime()!=null,"createtime",vo.getStartTime());
        queryWrapper.le(vo.getEndTime()!=null,"createtime",vo.getEndTime());
        queryWrapper.orderByDesc("createtime");
        this.noticeService.page(page, queryWrapper);
        return new DataGrideView(page.getTotal(),page.getRecords());

    }

    /**
     * 添加公告
     * @param
     * @return
     */
    @RequestMapping("/addNotice")
    public Result addNotice(NoticeVo noticeVo,HttpSession session){
        try {

             noticeVo.setCreatetime(new Date());
            User user = (User)session.getAttribute("user");
            noticeVo.setOpername(user.getName());
            this.noticeService.save(noticeVo);
            return Result.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADD_FAILE;
        }
    }
    /**
     * 修改公告
     * @param
     * @return
     */
    @RequestMapping("/updateNotice")
    public Result addNotice(NoticeVo noticeVo){
        try {
            this.noticeService.updateById(noticeVo);
            return Result.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.UPDATE_FAILE;
        }
    }

    /**
     * 单条删除
     * @param id
     * @return
     */
    @RequestMapping("/deletenotice")
    public Result deleteNotice(Integer id){
        try {
            this.noticeService.removeById(id);
            return Result.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.DELETE_FAILE;
        }

    }
    /**
     * 批量删除
     */

    @RequestMapping("/batchDeletenotice")
    public Result batchNoticeDelete(NoticeVo info){
        try {
            Collection<Serializable> ids = new ArrayList<Serializable>();
            for (Integer id : info.getIds()){
                ids.add(id);
            }
            this.noticeService.removeByIds(ids);
            return Result.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.DELETE_FAILE;
        }
    }


}
