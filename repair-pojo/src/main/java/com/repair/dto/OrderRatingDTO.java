package com.repair.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRatingDTO implements Serializable {
    private static final long serialVersionUID = 6L;

    @NotNull(message = "评分不能为空")
    @Min(value = 1, message = "评分必须在1到5之间")
    @Max(value = 5, message = "评分必须在1到5之间")
    private Integer rating;

    @Size(max = 500, message = "评价内容不能超过500字符")
    private String comment;
}
