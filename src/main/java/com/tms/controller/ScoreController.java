package com.tms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tms.dto.CodeMsg;
import com.tms.dto.Result;
import com.tms.dto.ScoreSetRequestDto;
import com.tms.dto.StudentLeaderChooseRequestDto;
import com.tms.entity.RoleUser;
import com.tms.entity.Score;
import com.tms.entity.User;
import com.tms.inter_face.PassToken;
import com.tms.mapper.ScoreMapper;
import com.tms.mapper.TeamMapper;
import com.tms.mapper.UserMapper;
import com.tms.utils.ThreadLocalUtil;
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
@RequestMapping("/score")
public class ScoreController {
    @Resource
    UserMapper userMapper;

    @Resource
    TeamMapper teamMapper;

    @Resource
    ScoreMapper scoreMapper;

    //老师给小组评分
    @PassToken
    @PostMapping("/set")
    public Result<String> setLeader(@RequestBody ScoreSetRequestDto request){
        QueryWrapper<Score> wrapper = new QueryWrapper<>();
        User u= ThreadLocalUtil.getCurrentUser();
        int userid=userMapper.getId(u.getAccount());
        int teamid=teamMapper.getId(request.getTeamName());
        wrapper.eq("team_id",teamid);
        Score s=scoreMapper.selectOne(wrapper);
        if(null==s){
            s=new Score();
            s.setScore(request.getScore());
            s.setUserId(userid);
            s.setTeamId(teamid);
            scoreMapper.insert(s);
            return Result.success("success");
        }
        return Result.error(CodeMsg.REPEAT_INSERT);

    }


}
