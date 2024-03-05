package com.repair.vo;

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
public class FeedbackSearchVO implements Serializable {
    private static final long serialVersionUID = 6L;

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
