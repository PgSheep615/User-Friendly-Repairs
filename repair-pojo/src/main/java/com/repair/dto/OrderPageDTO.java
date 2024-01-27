package com.repair.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LZB
 * @date 2024/1/18
 * @Description
 */
@Data
public class OrderPageDTO {
    //页码
    private Integer page;

    //每页记录数
    private Integer pageSize;

    //TODO 可以根据维修单状态、距离筛选

    //用户id
    private Long id;
}
