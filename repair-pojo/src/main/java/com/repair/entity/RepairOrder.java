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
 * 维修单信息表
 * @TableName repair_order
 */
@TableName(value ="repair_order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepairOrder implements Serializable {
    /**
     * 维修单ID，主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 关联用户ID
     */
    private Long userId;

    /**
     * 电脑机型
     */
    private String computerModel;

    /**
     * 电脑品牌型号
     */
    private String computerBrandModel;

    /**
     * 操作系统版本
     */
    private String osVersion;

    /**
     * 详细描述故障问题情况
     */
    private String faultDescription;

    /**
     * 故障情况图片，可以存储图片路径或序列化后的数据
     */
    private String faultImages;

    /**
     * 是否被接单，1表示已接单，0表示未接单
     */
    private Integer isAccepted;

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