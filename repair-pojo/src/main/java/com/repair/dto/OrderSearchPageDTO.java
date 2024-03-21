package com.repair.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author LZB
 * @date 2024/3/6
 * @Description
 */
@Data
public class OrderSearchPageDTO implements Serializable {

    private static final long serialVersionUID = 6L;
    //页码
    private int page;


    //每页记录数
    private int pageSize;
}
