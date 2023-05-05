package com.tms.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.tms.dto.PageRequestDto;
import com.tms.dto.Result;
import com.tms.dto.StudentDelRequestDto;
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



    @PostMapping("/delete")
    public Result<String> delete(@RequestBody StudentDelRequestDto requestDto){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account",requestDto.getAccount());
        userMapper.delete(wrapper);
        System.out.println(requestDto.getAccount());
        System.out.println("我到删除学生这了");
        return Result.success("success");

    }

}
