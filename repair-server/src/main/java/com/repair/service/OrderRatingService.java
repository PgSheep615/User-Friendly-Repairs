package com.repair.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.repair.dto.OrderRatingDTO;
import com.repair.dto.OrderSearchPageDTO;
import com.repair.entity.OrderRating;
import com.repair.result.PageResult;
import com.repair.vo.OrderRatingVO;

public interface OrderRatingService extends IService<OrderRating> {

    void submitRating(Long orderId, OrderRatingDTO orderRatingDTO);

    OrderRatingVO getRatingByOrderId(Long orderId);

    PageResult getRatingsByAdmin(OrderSearchPageDTO orderSearchPageDTO);
}
