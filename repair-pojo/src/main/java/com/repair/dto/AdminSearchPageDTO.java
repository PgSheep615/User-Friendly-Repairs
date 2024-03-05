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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminSearchPageDTO implements Serializable {
    private static final long serialVersionUID = 6L;


    //页码
    private int page;

    //每页记录数
    private int pageSize;
}
