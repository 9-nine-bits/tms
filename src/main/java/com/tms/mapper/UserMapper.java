package com.tms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tms.dto.StudentPageDto;
import com.tms.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
public interface UserMapper extends BaseMapper<User> {
    @Select("select account,username,")
    IPage<StudentPageDto> selectByPage(IPage<User> userPage , @Param(Constants.WRAPPER) Wrapper<User> userWrapper);

}