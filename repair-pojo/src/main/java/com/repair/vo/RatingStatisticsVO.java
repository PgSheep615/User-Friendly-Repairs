package com.repair.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingStatisticsVO implements Serializable {
    private static final long serialVersionUID = 6L;

    private Long totalCount;

    private Double averageRating;
}
