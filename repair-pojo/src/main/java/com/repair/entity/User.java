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
 * 用户信息表
 * @TableName user
 */
@TableName(value ="user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    /**
     * 用户ID，主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户的openid
     */
    private String openid;

    /**
     * 姓名
     */
    private String name;
    /**
     * 密码
     */
    @TableField(exist = false)
    private String password;

    /**
     * 学号
     */
    private String studentId;

    /**
     * 所在校区
     */
    private String campus;

    /**
     * 所在院系
     */
    private String department;

    /**
     * 手机长号
     */
    private String phoneNumber;

    /**
     * 微信号
     */
    private String wechatId;

    /**
     * 地址（精确到宿舍号）
     */
    private String address;

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

    /**
     * 用户头像
     */
    private String image;

    @TableField(exist = false)
    private static final long serialVersionUID = 6L;
}