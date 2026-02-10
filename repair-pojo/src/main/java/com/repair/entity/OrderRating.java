package com.repair.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName(value = "order_rating")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRating implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long orderId;

    private Long userId;

    private Long adminId;

    private Integer rating;

    private String comment;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;

    @TableLogic
    private Integer isDeleted;

    private LocalDateTime deleteTime;

    @Version
    private Integer version;

    @TableField(exist = false)
    private static final long serialVersionUID = 6L;
}
