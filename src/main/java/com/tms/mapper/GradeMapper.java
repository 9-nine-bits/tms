package com.tms.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tms.dto.GradeResponseDto;
import com.tms.dto.StudentPageDto;
import com.tms.entity.Grade;
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
public interface GradeMapper extends BaseMapper<Grade> {
    @Select("select account, username, score from  `user` u inner join grade g  on u.id=g.user_id where u.is_delete!=1")
    IPage<GradeResponseDto> selectByPage(IPage<Grade> gradePage);
}
