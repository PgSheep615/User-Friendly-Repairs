package com.repair.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author LZB
 * @date 2024/1/18
 * @Description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCommunityVO implements Serializable {
    /**
     * 维修单ID，主键
     */
    private Long id;
    /**
     * 姓名
     */
    private String name;
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
     * 订单修改时间，每次更新时自动更新为当前时间
     */
    private LocalDateTime updateTime;
}
