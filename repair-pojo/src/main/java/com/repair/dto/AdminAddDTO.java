package com.repair.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author LZB
 * @date 2024/1/27
 * @Description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminAddDTO implements Serializable {

    private Long userId;

    private String groupName;
}
