package com.tms.service.impl;

import com.tms.entity.TeamUser;
import com.tms.mapper.TeamUserMapper;
import com.tms.service.ITeamUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuchuang
 * @since 2023-04-19
 */
@Service
public class TeamUserServiceImpl extends ServiceImpl<TeamUserMapper, TeamUser> implements ITeamUserService {

}
