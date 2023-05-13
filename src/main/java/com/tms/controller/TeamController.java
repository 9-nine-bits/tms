package com.tms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tms.dto.*;
import com.tms.entity.Team;
import com.tms.entity.TeamUser;
import com.tms.entity.User;
import com.tms.inter_face.UserLoginToken;
import com.tms.mapper.TeamMapper;
import com.tms.mapper.TeamUserMapper;
import com.tms.mapper.UserMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
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
@RequestMapping("/team")
public class TeamController {

    @Resource
    TeamMapper teamMapper;

    @Resource
    TeamUserMapper teamUserMapper;
    @Resource
    UserMapper userMapper;


    //查看小组情况
    @UserLoginToken
    @PostMapping("/all")
    public Result<List<TeamPageResponseDTO>> getAllTeam(@RequestBody PageRequestDto pageRequestDto) throws IOException, ClassNotFoundException {
        // QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        //LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();
        // userLambdaQueryWrapper.like(User::getUsername,"wuchuang");
        Page<Team> page=new Page<>(pageRequestDto.getPageNum(),pageRequestDto.getPageSize(),false);
        // User u=userService.getOne(userLambdaQueryWrapper);
        // System.out.println(u.getUsername()+u.getPassword());
        IPage<TeamPageDto> iPage=teamMapper.selectByPage(page);
        List<TeamPageDto> list=iPage.getRecords();
        List<TeamPageResponseDTO> res=new ArrayList<>();
        for(TeamPageDto t:list){
            QueryWrapper<TeamUser> tm=new QueryWrapper<>();
            int teamid=teamMapper.getId(t.getTeamName());
            tm.eq("team_id",teamid);
            List<TeamUser> tList=teamUserMapper.selectList(tm);
            List<StudentPageDto> slist=new ArrayList<>();
            TeamPageDto tp=teamMapper.selectTeam(teamid);
            QueryWrapper<Team> wrapper1=new QueryWrapper<>();
            wrapper1.eq("team_name",tp.getTeamName());
            Team team=teamMapper.selectOne(wrapper1);
            int leaderid=team.getTeamLeaderId();
            StudentPageDto sp1= userMapper.select(leaderid);
            slist.add(sp1);
            for(TeamUser s:tList){
                StudentPageDto sp= userMapper.select(s.getUserId());

                slist.add(sp);
            }
            TeamPageResponseDTO te=new TeamPageResponseDTO(t,slist);
            res.add(te);
        }

        // System.out.println(iPage.getRecords().get(0));
        return Result.success(res, iPage.getTotal());
    }

}
