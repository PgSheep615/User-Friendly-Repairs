package com.repair.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LZB
 * @date 2024/1/27
 * @Description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminAddDTO {

    private Long userId;

    private String groupName;
}
