package com.tms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tms.dto.GradeResponseDto;
import com.tms.dto.PageRequestDto;
import com.tms.dto.Result;
import com.tms.entity.Grade;
import com.tms.inter_face.PassToken;
import com.tms.mapper.GradeMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
@RequestMapping("/grade")
public class GradeController {

    @Resource
    GradeMapper gradeMapper;
    @PassToken
    @PostMapping("/all")
    public Result<List<GradeResponseDto>> allGrade(@RequestBody PageRequestDto requestDto){
        Page<Grade> page=new Page<>(requestDto.getPageNum(),requestDto.getPageSize(),false);
        IPage<GradeResponseDto> iPage=gradeMapper.selectByPage(page);
        return Result.success(iPage.getRecords(),iPage.getTotal());
    }




}
