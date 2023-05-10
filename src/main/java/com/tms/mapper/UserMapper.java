package com.tms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tms.dto.StudentLeaderChooseRequestDto;
import com.tms.dto.StudentPageDto;
import com.tms.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
public interface UserMapper extends BaseMapper<User> {
    @Select("select account, username,phone,gender from  role left join (`user` u left join role_user ru on u.id=ru.user_id)  on role.id=ru.role_id where role_name='学生' and u.is_delete!=1")
    IPage<StudentPageDto> selectByPage(IPage<User> userPage);
    @Select("select id from user where account = #{account} and user.is_delete!=1" )
    Integer getId(String account);

    @Select("select account, username,phone,gender from  user  where user.id=#{id} and user.is_delete!=1")
    StudentPageDto select(int id);


}
