package com.tms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tms.dto.CodeMsg;
import com.tms.dto.LoginRequestDto;
import com.tms.dto.LoginResponseDto;
import com.tms.dto.Result;
import com.tms.entity.Role;
import com.tms.entity.User;
import com.tms.inter_face.PassToken;
import com.tms.service.IUserService;
import com.tms.utils.TokenUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wuchuang
 * @since 2023-04-19
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    IUserService userService;

    @PassToken
    @PostMapping("/login")
    public Result<LoginResponseDto> login(@RequestBody LoginRequestDto request){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        System.out.println("我到这了");
        wrapper.eq("accout", request.getAccount());
        System.out.println(request.getAccount());
        User u=userService.getOne(wrapper);
        LoginResponseDto responseDto=new LoginResponseDto();
        if(request.getPassword().equals(u.getPassword())){
           String token= TokenUtils.token(request.getAccount(),request.getPassword());
           responseDto.setToken(token);
           return Result.success(responseDto);
        }
        return Result.error(CodeMsg.PASSWORD_ERROR);
    }





}
