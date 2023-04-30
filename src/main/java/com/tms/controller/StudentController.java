package com.tms.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.tms.dto.PageRequestDto;
import com.tms.dto.Result;
import com.tms.dto.StudentPageDto;
import com.tms.entity.Role;
import com.tms.entity.User;

import com.tms.mapper.UserMapper;
import com.tms.service.IRoleService;
import com.tms.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @PostMapping("/all")
    public Result<List<User>> getAllStudent(PageRequestDto pageRequestDto){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();
        userLambdaQueryWrapper.like(User::getUsername,"wuchuang");
        Page<User> page=new Page<>(pageRequestDto.getPageNum(),pageRequestDto.getPageSize(),false);
        User u=userService.getOne(userLambdaQueryWrapper);
        System.out.println(u.getUsername()+u.getPassword());
        IPage<User> iPage=userMapper.selectPage(page,userLambdaQueryWrapper);
        System.out.println(iPage.getRecords().get(0));
        return Result.success(page.getRecords());
    }
// 组装查询条件

}
