package com.repair.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author LZB
 * @date 2024/1/27
 * @Description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchVO implements Serializable {
    private static final long serialVersionUID = 6L;

    /**
     * 用户ID，主键
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
     * 是否删除，1表示已删除，0表示未删除
     */
    private Integer isDeleted;

}
