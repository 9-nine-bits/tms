package com.tms.mapper;

import com.tms.entity.LessonStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface LessonStatusMapper extends BaseMapper<LessonStatus> {

    @Select("select id from lesson_status where status = #{status} and lesson_status.is_delete!=1" )
    Integer getId(String status);

}
