package com.tms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tms.dto.CodeMsg;
import com.tms.dto.Result;
import com.tms.dto.StageScoreInsertRequestDto;
import com.tms.dto.StudentLeaderChooseRequestDto;
import com.tms.entity.LessonStatus;
import com.tms.entity.StageScore;
import com.tms.inter_face.PassToken;
import com.tms.inter_face.UserLoginToken;
import com.tms.mapper.LessonStatusMapper;
import com.tms.mapper.StageScoreMapper;
import com.tms.mapper.UserMapper;
import com.tms.service.IStageScoreService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wuchuang
 * @since 2023-04-19
 */
@RestController
@RequestMapping("/stage-score")
public class StageScoreController {
    @Resource
    StageScoreMapper stageScoreMapper;

    @Resource
    IStageScoreService stageScoreService;

    @Resource
    UserMapper userMapper;

    @Resource
    LessonStatusMapper lessonStatusMapper;

    //阶段性打分
    @UserLoginToken
    @PostMapping("/insert")
    public Result<String> insert(@RequestBody StageScoreInsertRequestDto request){
        StageScore s=new StageScore();
        int userid=userMapper.getId(request.getAccount());
        int lesstutasid=lessonStatusMapper.getId(request.getLessonStatus());
        QueryWrapper<StageScore> wrapper=new QueryWrapper();
        wrapper.eq("user_id",userid);
        wrapper.eq("lesson_status_id",lesstutasid);
        System.out.println(userid);
        System.out.println(lesstutasid);
        System.out.println("//////");
        StageScore tmp=stageScoreService.getOne(wrapper);
        //  System.out.println(tmp.getCurStatusScore());
        if(null!=tmp){
        return Result.error(CodeMsg.REPEAT_INSERT);
        }
        s.setUserId(userid);
        s.setCurStatusScore(request.getCurStatusScore());
        s.setLessonStatusId(lessonStatusMapper.getId(request.getLessonStatus()));
        stageScoreMapper.insert(s);
        return Result.success("success");

    }




}
