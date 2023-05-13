package com.tms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tms.dto.*;
import com.tms.entity.LessonStatus;
import com.tms.entity.User;
import com.tms.inter_face.UserLoginToken;
import com.tms.mapper.LessonStatusMapper;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wuchuang
 * @since 2023-04-19
 */
@RestController
@RequestMapping("/lesson-status")
public class LessonStatusController {
    @Resource
    LessonStatusMapper lessonStatusMapper;
    @UserLoginToken
    @PostMapping("/all")
    public Result<List<LessonStatusResponseDto>> getAllStatus(){
        QueryWrapper<LessonStatus> wrapper=new QueryWrapper<>();
        List<LessonStatus> l=lessonStatusMapper.selectList(wrapper);
        List<LessonStatusResponseDto> res=new ArrayList<>();
        for(LessonStatus s:l){
            LessonStatusResponseDto ll=new LessonStatusResponseDto();
            ll.setStatus(s.getStatus());
            ll.setStartTime(s.getStartTime());
            ll.setEndTime(s.getEndTime());
            res.add(ll);
        }
        return Result.success(res);
    }


    //更新课程起止时间，前端需要做时间校验
    @UserLoginToken
    @PostMapping("/updateTime")
    public Result<String> updateTime(@RequestBody LessonStatusUpdateRequestDto requestDto){
        QueryWrapper<LessonStatus> wrapper=new QueryWrapper<>();
        wrapper.eq("status",requestDto.getStatus());
        LessonStatus l=lessonStatusMapper.selectOne(wrapper);
        l.setStartTime(requestDto.getStartTime());
        l.setEndTime(requestDto.getEndTime());
        lessonStatusMapper.update(l,wrapper);

        return Result.success("success");
    }

    @UserLoginToken
    @PostMapping("/getStatus")
    public Result<Integer> getStatus(){
        QueryWrapper<LessonStatus> wrapper=new QueryWrapper<>();
        List<LessonStatus> l=lessonStatusMapper.selectList(wrapper);
        Date date=new Date(System.currentTimeMillis());
        Integer res=0;
        for(LessonStatus s:l){
            res++;
            if(date.compareTo(s.getStartTime())>=0&&date.compareTo(s.getEndTime())<0){
                return Result.success(res);
            }
        }
        return Result.error(CodeMsg.STATUS_NOT_IN);
    }


}
