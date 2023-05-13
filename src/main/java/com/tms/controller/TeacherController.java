package com.tms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tms.dto.*;
import com.tms.entity.RoleUser;
import com.tms.entity.User;
import com.tms.inter_face.PassToken;
import com.tms.inter_face.UserLoginToken;
import com.tms.mapper.RoleUserMapper;
import com.tms.mapper.UserMapper;
import com.tms.service.IRoleService;
import com.tms.service.IUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Resource
    IUserService userService;
    @Resource
    IRoleService roleService;
    @Resource
    UserMapper userMapper;

    @Resource
    RoleUserMapper roleUserMapper;
    //暂时弃用
    @UserLoginToken
    @PostMapping("/setLeader")
    public Result<String> setLeader(@RequestBody StudentLeaderChooseRequestDto request){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account",request.getAccount());
        User u=userService.getOne(wrapper);
        if(u!=null){
            RoleUser r=new RoleUser();
            r.setRoleId(2);
            r.setUserId(userMapper.getId(u.getAccount()));
            roleUserMapper.insert(r);
            return Result.success("success");
        }
        return Result.error(CodeMsg.USER_NOT_EXITS);

    }


}
