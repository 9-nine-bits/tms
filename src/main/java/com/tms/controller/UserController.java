package com.tms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tms.dto.*;
import com.tms.entity.Role;
import com.tms.entity.User;
import com.tms.inter_face.PassToken;
import com.tms.service.IUserService;
import com.tms.utils.ThreadLocalUtil;
import com.tms.utils.TokenUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
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
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Resource
    IUserService userService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @PassToken
    @PostMapping("/login")
    public Result<LoginResponseDto> login(@RequestBody LoginRequestDto request){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        System.out.println("我到这了");
        wrapper.eq("account", request.getAccount());
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



    //修改密码第一步
    @PassToken
    @PostMapping("/find")
    public Result<AccountFindResponseDto> find(@RequestBody AccountFindRequestDto requestDto){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account",requestDto.getAccount());
        User u=userService.getOne(wrapper);
        if(u!=null){
            AccountFindResponseDto responseDto=new AccountFindResponseDto(u);
            redisTemplate.opsForValue().set(u.getAccount(),"1",60*10, TimeUnit.SECONDS);

            return Result.success(responseDto);
        }
        return Result.error(CodeMsg.USER_NOT_EXITS);
    }


    //修改密码第二步
    @PassToken
    @PostMapping("/check")
    public Result<String> check(@RequestBody AccountFindResponseDto request){
        if(null!=redisTemplate.opsForValue().get(request.getAccount())){
            if("1".equals(redisTemplate.opsForValue().get(request.getAccount()))){
                QueryWrapper<User> wrapper = new QueryWrapper<>();
                wrapper.eq("account",request.getAccount());
                User u=userService.getOne(wrapper);
                if(u.getAnswer1().equals(request.getAnswer1())&&u.getAnswer2().equals(request.getAnswer2())){
                    redisTemplate.opsForValue().set(u.getAccount(),"2",60*10, TimeUnit.SECONDS);
                    return Result.success("success");
                }
            }
        }
        return  Result.error(CodeMsg.PASSWORD_ERROR);
    }


    //修改密码第三步
    @PassToken
    @PostMapping("/modify")
    public Result<String> modify(@RequestBody ModifyPwdRequest request){
        if(null!=redisTemplate.opsForValue().get(request.getAccount())){
            if("2".equals(redisTemplate.opsForValue().get(request.getAccount()))){
                QueryWrapper<User> wrapper = new QueryWrapper<>();
                wrapper.eq("account",request.getAccount());
                User u=userService.getOne(wrapper);
                u.setPassword(request.getPassword());
                userService.update(u,wrapper);
                redisTemplate.delete(request.getAccount());
                return Result.success("success");
            }
        }
        return  Result.error(CodeMsg.PASSWORD_ERROR);
    }


}
