package com.repair.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    private static final long serialVersionUID = 6L;


    /**
     * 反馈问题情况
     */
    @NotEmpty(message = "反馈问题情况不能为空")
    private String feedbackDescription;
}
