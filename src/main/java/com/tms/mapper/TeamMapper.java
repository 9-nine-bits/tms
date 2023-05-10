package com.tms.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tms.dto.TeamPageDto;
import com.tms.dto.TopicAllResponseDto;
import com.tms.entity.Team;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tms.entity.Topic;
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
public interface TeamMapper extends BaseMapper<Team> {
    @Select("select id from team where team_name = #{teamName} and team.is_delete!=1" )
    Integer getId(String teamName);

    @Select("select team_name,u.username as team_leader,team_capacity,team_cur_capacity,topic.topic_name as topic from topic inner join(team t inner join `user` u on t.team_leader_id=u.id ) on topic.id=t.team_topic_id where t.is_delete!=1")
    IPage<TeamPageDto> selectByPage(IPage<Team> teamPage);
}
