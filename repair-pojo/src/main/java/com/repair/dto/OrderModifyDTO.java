package com.repair.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单管理界面
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderModifyDTO {
    /**
     * 维修单ID，主键
     */
    private Long id;
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


}