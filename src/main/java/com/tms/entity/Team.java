package com.tms.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuchuang
 * @since 2023-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    private String teamName;

    private Integer teamLeaderId;

    private String teamCapacity;

    private String teamCurCapacity;

    private Integer teamTopicId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Boolean isDelete;

    /**
     * 数据更新版本
     */
    @Version
    private Long version;


}
