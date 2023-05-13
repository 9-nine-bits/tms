package com.tms.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.tms.dto.*;
import com.tms.entity.*;

import com.tms.inter_face.PassToken;
import com.tms.inter_face.UserLoginToken;
import com.tms.mapper.*;
import com.tms.service.IRoleService;
import com.tms.service.IUserService;
import com.tms.utils.ThreadLocalUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
@RequestMapping("/student")
public class StudentController {
    @Resource
    IUserService userService;

    @Resource
    UserMapper userMapper;

    @Resource
    TeamMapper teamMapper;

    @Resource
    TopicMapper topicMapper;

    @Resource
    TeamLessonMapper teamLessonMapper;

    @Resource
    TeamUserMapper teamUserMapper;



    @UserLoginToken
    @PostMapping("/all")
    public Result<List<StudentPageDto>> getAllStudent(@RequestBody PageRequestDto pageRequestDto){
       // QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        //LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();
       // userLambdaQueryWrapper.like(User::getUsername,"wuchuang");
        Page<User> page=new Page<>(pageRequestDto.getPageNum(),pageRequestDto.getPageSize(),false);
       // User u=userService.getOne(userLambdaQueryWrapper);
       // System.out.println(u.getUsername()+u.getPassword());
        IPage<StudentPageDto> iPage=userMapper.selectByPage(page);
       // System.out.println(iPage.getRecords().get(0));
        return Result.success(iPage.getRecords(), iPage.getTotal());
    }



    //修改学生信息，账号不能更，前端尽量传进来的时候所有字段都不为空
    @UserLoginToken
    @PostMapping("/update")
    public Result<String> update(@RequestBody StudentPageDto requestDto){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account",requestDto.getAccount());
        User u=userMapper.selectOne(wrapper);
        if(null!=requestDto.getGender()){
            u.setGender(requestDto.getGender());
        }
        if(null!=requestDto.getPhone()){
            u.setPhone(requestDto.getPhone());
        }
        if(null!=requestDto.getUsername()){
            u.setUsername(requestDto.getUsername());
        }
        userMapper.update(u,wrapper);
        return Result.success("success");

    }


    //获取当前学生的个人信息

    @UserLoginToken
    @PostMapping("/getUser")
    public Result<StudentInfoResponseDto> getUser(){
        User u=ThreadLocalUtil.getCurrentUser();
        System.out.println(u);
        StudentInfoResponseDto studentInfoResponseDto=new StudentInfoResponseDto(u);
        return Result.success(studentInfoResponseDto);
    }


    //学生设置个人资料
    @UserLoginToken
    @PostMapping("/UpdateCurUser")
    public Result<String> UpdateCurUser(StudentInfoResponseDto responseDto){
        User u=ThreadLocalUtil.getCurrentUser();
        QueryWrapper<User> wrapper=new QueryWrapper();
        wrapper.eq("account",u.getAccount());
        u.setPhone(responseDto.getPhone());
        u.setGender(responseDto.getGender());
        u.setPassword(responseDto.getPassword());
        u.setQuestion1(responseDto.getQuestion1());
        u.setQuestion2(responseDto.getQuestion2());
        u.setAnswer1(responseDto.getAnswer1());
        u.setAnswer2(responseDto.getAnswer2());
        return Result.success("success");
    }



    @UserLoginToken
    @PostMapping("/delete")
    public Result<String> delete(@RequestBody StudentDelRequestDto requestDto){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account",requestDto.getAccount());
        userMapper.delete(wrapper);
        System.out.println(requestDto.getAccount());
        return Result.success("success");

    }


    //选题，选题的时候创建小组，同时开启学生选题阶段
    @UserLoginToken
    @PostMapping("/select")
    public Result<String> select(@RequestBody TeamCreateRequestDto requestDto){
        QueryWrapper<Topic> wrapper = new QueryWrapper<>();
        User u=ThreadLocalUtil.getCurrentUser();
        int userid=userMapper.getId(u.getAccount());
        wrapper.eq("topic_name",requestDto.getTopicName());
        Topic topic=topicMapper.selectOne(wrapper);
        if(topic.getTopicSelectedNum()<topic.getTopicMaxNum()) {
            Team team = new Team();
            team.setTeamName(requestDto.getTeamName());
            team.setTeamCapacity(topic.getTopicCapacity());
            team.setTeamCurCapacity("1");
            team.setTeamLeaderId(userid);
            team.setTeamTopicId(topicMapper.getId(topic.getTopicName()));
            teamMapper.insert(team);
            topic.setTopicSelectedNum(topic.getTopicSelectedNum()+1);
            //选题数加一
            topicMapper.update(topic,wrapper);
            TeamLesson teamLesson=new TeamLesson();
            int teamid=teamMapper.getId(team.getTeamName());
            teamLesson.setStatusId(1);
            teamLesson.setTeamId(teamid);
            teamLessonMapper.insert(teamLesson);
            return Result.success("success");
        }
        return Result.error(CodeMsg.NUMBER_ERROR);

    }


    //随机加入小组
    @UserLoginToken
    @PostMapping("/randomjoin")
    public Result<String> randomjoin(){
        User u=ThreadLocalUtil.getCurrentUser();
        int userid=userMapper.getId(u.getAccount());
        int teamid=teamMapper.selectRandom();
        QueryWrapper<TeamUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userid);
        TeamUser t=teamUserMapper.selectOne(wrapper);
        if(null==t){
            t=new TeamUser();
            t.setTeamId(teamid);
            t.setUserId(userid);
            teamUserMapper.insert(t);
            //当前选题人数加一
            TeamPageDto tm=teamMapper.selectTeam(teamid);
            QueryWrapper<Team> wrapper1 = new QueryWrapper<>();
            wrapper.eq("team_name",tm.getTeamName());
            Team t1=teamMapper.selectOne(wrapper1);
            if(t1.getTeamCurCapacity().compareTo(t1.getTeamCapacity())<1){
                t1.setTeamCurCapacity(t1.getTeamCurCapacity()+1);
            }
            teamMapper.update(t1,wrapper1);
            return Result.success("success");
        }

        return Result.error(CodeMsg.REPEAT_TEAMINSERT);

    }

    @UserLoginToken
    @PostMapping("/join")
    public Result<String> join(@RequestBody TeamJoinRequestDto requestDto){
        User u=ThreadLocalUtil.getCurrentUser();
        int userid=userMapper.getId(u.getAccount());
        int teamid=teamMapper.getId(requestDto.getTeamName());
        QueryWrapper<TeamUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userid);
        TeamUser t=teamUserMapper.selectOne(wrapper);
        if(null==t){
            t=new TeamUser();
            t.setTeamId(teamid);
            t.setUserId(userid);
            teamUserMapper.insert(t);
            //当前选题人数加一
            TeamPageDto tm=teamMapper.selectTeam(teamid);
            QueryWrapper<Team> wrapper1 = new QueryWrapper<>();
            wrapper.eq("team_name",tm.getTeamName());
            Team t1=teamMapper.selectOne(wrapper1);
            if(t1.getTeamCurCapacity().compareTo(t1.getTeamCapacity())<1){
                t1.setTeamCurCapacity(t1.getTeamCurCapacity()+1);
            }
            teamMapper.update(t1,wrapper1);
            return Result.success("success");
        }

        return Result.error(CodeMsg.REPEAT_TEAMINSERT);

    }

    //退出小组
    @UserLoginToken
    @PostMapping("/quit")
    public Result<String> quit(@RequestBody TeamJoinRequestDto requestDto){
        User u=ThreadLocalUtil.getCurrentUser();
        int userid=userMapper.getId(u.getAccount());
        int teamid=teamMapper.getId(requestDto.getTeamName());
        QueryWrapper<TeamUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userid);
        TeamUser t=teamUserMapper.selectOne(wrapper);
        if(null!=t){
            teamUserMapper.delete(wrapper);
            return Result.success("success");
        }

        return Result.error(CodeMsg.REPEAT_TEAMINSERT);

    }

    //组长踢出小组人员(前端需设置组长只能踢本组的，即只有本组的成员后面有踢出按钮（后端暂时未做校验）)
    @UserLoginToken
    @PostMapping("/kick")
    public Result<String> kick(@RequestBody StudentDelRequestDto requestDto){

        int userid=userMapper.getId(requestDto.getAccount());
        QueryWrapper<TeamUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userid);
        TeamUser t=teamUserMapper.selectOne(wrapper);
        if(null!=t){
            teamUserMapper.delete(wrapper);
            return Result.success("success");
        }

        return Result.error(CodeMsg.REPEAT_TEAMINSERT);

    }

    @UserLoginToken
    @PostMapping("/isLeader")
    public Result<Boolean> isLeader(){
        User u=ThreadLocalUtil.getCurrentUser();
        int userid=userMapper.getId(u.getAccount());
        QueryWrapper<Team> wrapper=new QueryWrapper();
        wrapper.eq("team_leader_id",userid);
        Team t=teamMapper.selectOne(wrapper);
        if(null!=t){
            return Result.success(true);
        }

        return Result.success(false);

    }


    //返回自己小组的信息，用户第一个是组长信息
    @UserLoginToken
    @PostMapping("/getTeam")
    public Result<TeamPageResponseDTO> getTeam() throws IOException, ClassNotFoundException {
        User u=ThreadLocalUtil.getCurrentUser();
        int userid=userMapper.getId(u.getAccount());

        QueryWrapper<TeamUser> wrapper=new QueryWrapper<>();
        wrapper.eq("team_id",userid);
        TeamUser t=teamUserMapper.selectOne(wrapper);

        if(null!=t){
            int teamid=t.getTeamId();
            TeamPageDto tp=teamMapper.selectTeam(teamid);
            QueryWrapper<Team> wrapper1=new QueryWrapper<>();
            wrapper1.eq("team_name",tp.getTeamName());
            Team team=teamMapper.selectOne(wrapper1);
            int leaderid=team.getTeamLeaderId();
            QueryWrapper<TeamUser> tm=new QueryWrapper<>();
            tm.eq("team_id",teamid);
            List<TeamUser> tList=teamUserMapper.selectList(tm);
            List<StudentPageDto> slist=new ArrayList<>();
            StudentPageDto sp1= userMapper.select(leaderid);
            slist.add(sp1);
            for(TeamUser s:tList){
                StudentPageDto sp= userMapper.select(s.getUserId());
                slist.add(sp);
            }

            TeamPageResponseDTO res=new TeamPageResponseDTO(tp,slist);
            return Result.success(res);
        }
        return Result.error(CodeMsg.REPEAT_TEAMINSERT);

    }









}
