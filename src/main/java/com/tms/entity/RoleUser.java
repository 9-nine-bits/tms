package com.tms.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
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
public class RoleUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer roleId;

    private Integer userId;

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
    private Boolean isDelete;

    /**
     * 数据更新版本
     */
    private Long version;


}
