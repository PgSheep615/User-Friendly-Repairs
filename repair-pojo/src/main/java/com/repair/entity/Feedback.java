package com.repair.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户反馈信息表
 * @TableName feedback
 */
@TableName(value ="feedback")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feedback implements Serializable {
    /**
     * 反馈表ID，主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 关联用户ID
     */
    private Long userId;

    /**
     * 反馈问题情况
     */
    private String feedbackDescription;

    /**
     * 创建时间，自动填充当前时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间，每次更新时自动更新为当前时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建者ID
     */
    private Long createUser;

    /**
     * 修改者ID
     */
    private Long updateUser;

    /**
     * 是否删除，1表示已删除，0表示未删除
     */
    @TableLogic
    private Integer isDeleted;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;

    /**
     * 乐观锁版本号，用于并发控制
     */
    @Version
    private Integer version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}