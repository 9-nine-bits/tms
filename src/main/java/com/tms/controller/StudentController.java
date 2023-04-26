package com.tms.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.tms.entity.Role;
import com.tms.entity.User;
import com.tms.pagedto.Page;
import com.tms.service.IRoleService;
import com.tms.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

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
    @PostMapping("/all")
    public Page<User> getAllStudent(){
        HashMap map=new HashMap();
        String s;
        return null;
    }
}
