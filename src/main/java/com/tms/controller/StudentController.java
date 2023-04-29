package com.tms.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.tms.dto.PageRequestDto;
import com.tms.dto.StudentPageDto;
import com.tms.entity.Role;
import com.tms.entity.User;

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
    public Page<User> getAllStudent1(){
        HashMap map=new HashMap();
        String s;

        return null;
    }
    public PageInfo<StudentPageDto> getAllStudent(PageRequestDto pageRequestDto){
// 组装查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
// where age = 30
        queryWrapper.eq("age", 30);

// 查询第 2 页数据，每页 10 条
        Page<User> page = new Page<>(2, 10);
        userService.page();

        page = userService.selectPage(page, queryWrapper);
        System.out.println("总记录数：" + page.getTotal());
        System.out.println("总共多少页：" + page.getPages());
        System.out.println("当前页码：" + page.getCurrent());
// 当前页数据
        List<User> users = page.getRecords();
    }
}
