package com.repair.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author LZB
 * @date 2024/1/26
 * @Description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDTO implements Serializable {

    /**
     * 反馈问题情况
     */
    private String feedbackDescription;
}
