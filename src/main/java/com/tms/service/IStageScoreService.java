package com.tms.service;

import com.tms.entity.StageScore;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuchuang
 * @since 2023-04-19
 */
public interface IStageScoreService extends IService<StageScore> {

    public double cal(String account);





}
