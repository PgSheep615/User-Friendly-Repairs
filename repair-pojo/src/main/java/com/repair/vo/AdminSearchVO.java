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

/**
 * @author LZB
 * @date 2024/1/18
 * @Description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminSearchVO implements Serializable {
    /**
     * 管理员ID，主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 所在校区
     */
    private String campus;
    /**
     * 手机长号
     */
    private String phoneNumber;

    /**
     * 组别名称，如技术组等
     */
    private String groupName;
}
