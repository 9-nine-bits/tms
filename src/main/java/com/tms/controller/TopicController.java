package com.tms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tms.dto.PageRequestDto;
import com.tms.dto.Result;
import com.tms.dto.StudentPageDto;
import com.tms.dto.TopicAllResponseDto;
import com.tms.entity.User;
import com.tms.service.ITopicService;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("/topic")
public class TopicController {

    @Resource
    ITopicService topicService;


    public Result<TopicAllResponseDto> allTopic(PageRequestDto pageRequestDto){
        Page<User> page=new Page<>(pageRequestDto.getPageNum(),pageRequestDto.getPageSize(),false);
      //  IPage<StudentPageDto> iPage=topicService.selectByPage(page);

        return null;
    }

}
