package com.repair.vo;

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
public class FeedbackSearchVO {
    /**
     * 姓名
     */
    private String name;

    /**
     * 所在校区
     */
    private String campus;

    /**
     * 反馈问题情况
     */
    private String feedbackDescription;
}