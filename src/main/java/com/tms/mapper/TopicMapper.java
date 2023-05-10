package com.tms.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tms.dto.StudentPageDto;
import com.tms.dto.TopicAllResponseDto;
import com.tms.entity.Grade;
import com.tms.entity.Topic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tms.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wuchuang
 * @since 2023-04-19
 */
@Mapper

public interface TopicMapper extends BaseMapper<Topic> {
    @Select("select topic_name, topic_desc,topic_capacity,topic_max_num,topic_selected_num,is_top,is_check,create_user_id from  topic  where is_delete!=1")
    IPage<TopicAllResponseDto> selectByPage(IPage<Topic> topicPage);

    @Select("select id from topic where topic_name = #{topicName} and topic.is_delete!=1" )
    Integer getId(String topicName);

}
