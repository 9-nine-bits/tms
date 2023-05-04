package com.tms.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tms.dto.StudentPageDto;
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
    @Select("select account, username,phone,gender from  role left join (`user` u left join role_user ru on u.id=ru.user_id)  on role.id=ru.role_id where role_name='学生'")
    IPage<StudentPageDto> selectByPage(IPage<User> userPage);

}
