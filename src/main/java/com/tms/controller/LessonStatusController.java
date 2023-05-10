package com.tms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tms.dto.LessonStatusResponseDto;
import com.tms.dto.PageRequestDto;
import com.tms.dto.Result;
import com.tms.dto.StudentPageDto;
import com.tms.entity.LessonStatus;
import com.tms.entity.User;
import com.tms.mapper.LessonStatusMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
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

}
