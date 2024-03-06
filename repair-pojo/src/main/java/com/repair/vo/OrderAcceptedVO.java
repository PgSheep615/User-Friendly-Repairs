package com.repair.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author LZB
 * @date 2024/3/6
 * @Description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderAcceptedVO {
    /**
     * 维修单ID，主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;


    /**
     * 姓名
     */
    private String name;

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
     * 接单者ID
     */
    private Long accpetedUser;

    /**
     * 创建时间，自动填充当前时间
     */
    private LocalDateTime createTime;
}
