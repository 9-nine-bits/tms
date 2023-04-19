package com.tms.service.impl;

import com.tms.entity.Team;
import com.tms.mapper.TeamMapper;
import com.tms.service.ITeamService;
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
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements ITeamService {

}
