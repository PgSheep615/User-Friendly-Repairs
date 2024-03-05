package com.repair.dto;

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
public class OrderPageDTO implements Serializable {
    private static final long serialVersionUID = 6L;

    //页码
    private Integer page;

    //每页记录数
    private Integer pageSize;

    //TODO 可以根据维修单状态、距离筛选

    //用户id
    private Long id;
}
