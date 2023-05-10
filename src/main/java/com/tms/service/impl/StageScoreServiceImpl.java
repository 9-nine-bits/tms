package com.tms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tms.entity.StageScore;
import com.tms.mapper.StageScoreMapper;
import com.tms.mapper.UserMapper;
import com.tms.service.IStageScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.function.ToIntFunction;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuchuang
 * @since 2023-04-19
 */
@Service
public class StageScoreServiceImpl extends ServiceImpl<StageScoreMapper, StageScore> implements IStageScoreService {

    @Resource
    StageScoreMapper stageScoreMapper;
    @Resource
    UserMapper userMapper;

    @Override
    public double cal(String account) {
        QueryWrapper<StageScore> wrapper=new QueryWrapper();
        int userid=userMapper.getId(account);
        wrapper.eq("user_id",userid);
        stageScoreMapper.selectList(wrapper);

        double ave=  stageScoreMapper.selectList(wrapper).parallelStream().mapToInt(value -> Integer.parseInt(value.getCurStatusScore())).average().orElse(0.0);
        return ave;
    }
}
