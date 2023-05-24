package com.tms.mapper;

import com.tms.dto.StageScoreDto;
import com.tms.dto.StudentMessageDto;
import com.tms.entity.StageScore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wuchuang
 * @since 2023-04-19
 */
@Mapper
public interface StageScoreMapper extends BaseMapper<StageScore> {
    @Select("select user_id,round(avg(cast(cur_status_score as UNSIGNED integer)),0) as avg from stage_score group by user_id")
    List<StageScoreDto> selectAllStageScore();

}
