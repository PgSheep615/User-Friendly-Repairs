package com.repair.controller.order;

import com.repair.dto.OrderRatingDTO;
import com.repair.result.Result;
import com.repair.service.OrderRatingService;
import com.repair.vo.OrderRatingVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/user/order")
@RestController
@Api(tags = "订单评价接口")
@Slf4j
public class OrderRatingController {

    @Autowired
    private OrderRatingService orderRatingService;

    @PostMapping("/{orderId}/rating")
    @ApiOperation("提交订单评价")
    @PreAuthorize("hasAnyAuthority('user','admin')")
    public Result submitRating(@PathVariable Long orderId, @Valid @RequestBody OrderRatingDTO orderRatingDTO) {
        log.info("提交订单评价:{}", orderId);
        orderRatingService.submitRating(orderId, orderRatingDTO);
        return Result.success();
    }

    @GetMapping("/{orderId}/rating")
    @ApiOperation("查询订单评价")
    @PreAuthorize("hasAnyAuthority('user','admin')")
    public Result<OrderRatingVO> getRating(@PathVariable Long orderId) {
        OrderRatingVO orderRatingVO = orderRatingService.getRatingByOrderId(orderId);
        return Result.success(orderRatingVO);
    }
}
