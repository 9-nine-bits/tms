package com.tms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tms.dto.*;
import com.tms.entity.Topic;
import com.tms.entity.User;
import com.tms.inter_face.PassToken;
import com.tms.mapper.TopicMapper;
import com.tms.service.ITopicService;
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
@RequestMapping("/topic")
public class TopicController {

    @Resource
    TopicMapper topicMapper;

    @Resource
    ITopicService topicService;

    //查看所有选题
    @PassToken
    @PostMapping("/all")
    public Result<List<TopicAllResponseDto>> allTopic(@RequestBody PageRequestDto pageRequestDto){
        Page<Topic> page=new Page<>(pageRequestDto.getPageNum(),pageRequestDto.getPageSize(),false);
        IPage<TopicAllResponseDto> iPage=topicMapper.selectByPage(page);
        return Result.success(iPage.getRecords(),iPage.getTotal());

    }

    //删除选题，但是要先检测该选题是否有小组选中
    @PassToken
    @PostMapping("/delete")
    public Result<String> delete (@RequestBody TopicDelRequestDto requestDto){
        QueryWrapper<Topic> wrapper = new QueryWrapper<>();
        wrapper.eq("topic_name",requestDto.getTopicname());
        Topic t=topicService.getOne(wrapper);
        if(0==t.getTopicSelectedNum()){
            topicMapper.delete(wrapper);
            return Result.success("success");
        }
        return Result.error(CodeMsg.SELETED_ERROR);

    }

    //更新选题内容，更改的请求所有字段不能为空
    @PassToken
    @PostMapping("/update")
    public Result<String> update(@RequestBody TopicUpdateRequestDto requestDto){
        QueryWrapper<Topic> wrapper = new QueryWrapper<>();
        wrapper.eq("topic_name",requestDto.getOldName());
        Topic t=topicService.getOne(wrapper);
        if(null!=t){

        if(t.getTopicSelectedNum()<= requestDto.getTopicMaxNum()){
            t.setTopicName(requestDto.getTopicName());
            t.setTopicDesc(requestDto.getTopicDesc());
            t.setTopicCapacity(requestDto.getTopicCapacity());
            t.setTopicMaxNum(requestDto.getTopicMaxNum());
            t.setIsTop(t.getIsTop());
            topicService.update(t,wrapper);
            return Result.success("success");
        }
        return Result.error(CodeMsg.NUMBER_ERROR);
        }
        return Result.error(CodeMsg.TOPIC_NOT_EXITS);

    }

}
