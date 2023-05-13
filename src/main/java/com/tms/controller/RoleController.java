package com.tms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tms.entity.Role;
import com.tms.inter_face.UserLoginToken;
import com.tms.service.IRoleService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/role/{id}")
public class RoleController {
    @Resource
    IRoleService roleService;
    @UserLoginToken
    @RequestMapping
    public Role getRole(@PathVariable("id") int id){
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        return roleService.getOne(wrapper);
    }

}
