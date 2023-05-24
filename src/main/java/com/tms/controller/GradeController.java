package com.tms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tms.dto.*;
import com.tms.entity.*;
import com.tms.inter_face.PassToken;
import com.tms.inter_face.UserLoginToken;
import com.tms.mapper.GradeMapper;
import com.tms.mapper.ScoreMapper;
import com.tms.mapper.StageScoreMapper;
import com.tms.service.IUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
@RequestMapping("/grade")
public class GradeController {

    @Resource
    GradeMapper gradeMapper;
    @Resource
    ScoreMapper scoreMapper;

    @Resource
    StageScoreMapper stageScoreMapper;
    @Resource
    IUserService userService;
    @UserLoginToken
    @PostMapping("/all")
    public Result<List<GradeResponseDto>> allGrade(@RequestBody PageRequestDto requestDto){
        Page<Grade> page=new Page<>(requestDto.getPageNum(),requestDto.getPageSize(),false);
        IPage<GradeResponseDto> iPage=gradeMapper.selectByPage(page);
        return Result.success(iPage.getRecords(),iPage.getTotal());
    }
    //计算总分数
    @UserLoginToken
    @PostMapping("/cal")
    public Result<String> cal(){
        List<StageScoreDto> slist=stageScoreMapper.selectAllStageScore();
        for(StageScoreDto s:slist){
            int teamid=userService.getTeamId(s.getUserId());
            if(teamid!=-1){
                QueryWrapper<Score> wrapper=new QueryWrapper<>();
                wrapper.eq("team_id",teamid);
                Score score=scoreMapper.selectOne(wrapper);
                int scorenum=(Integer.parseInt(score.getScore())+s.getAvg())/2;
                Grade g=new Grade();
                g.setUserId(s.getUserId());
                g.setScore(String.valueOf(scorenum));
                gradeMapper.insert(g);
            }
        }

        return Result.success("success");

    }



}
