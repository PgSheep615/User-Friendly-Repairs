package com.repair.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.repair.constant.RepairOrderAcceptedConstant;
import com.repair.context.BaseContext;
import com.repair.dto.OrderRatingDTO;
import com.repair.dto.OrderSearchPageDTO;
import com.repair.entity.OrderRating;
import com.repair.entity.RepairOrder;
import com.repair.entity.User;
import com.repair.exception.RatingNotAllowedException;
import com.repair.mapper.OrderRatingMapper;
import com.repair.mapper.RepairOrderMapper;
import com.repair.mapper.UserMapper;
import com.repair.result.PageResult;
import com.repair.service.OrderRatingService;
import com.repair.vo.OrderRatingVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderRatingServiceImpl extends ServiceImpl<OrderRatingMapper, OrderRating> implements OrderRatingService {

    @Autowired
    private OrderRatingMapper orderRatingMapper;
    @Autowired
    private RepairOrderMapper repairOrderMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public void submitRating(Long orderId, OrderRatingDTO orderRatingDTO) {
        RepairOrder repairOrder = repairOrderMapper.selectById(orderId);
        if (repairOrder == null) {
            throw new RatingNotAllowedException("订单不存在");
        }
        Long currentUserId = BaseContext.getCurrentId();
        if (!repairOrder.getUserId().equals(currentUserId)) {
            throw new RatingNotAllowedException("无权限评价该订单");
        }
        if (!RepairOrderAcceptedConstant.Accepted.equals(repairOrder.getIsAccepted())) {
            throw new RatingNotAllowedException("订单未完成，不能评价");
        }
        QueryWrapper<OrderRating> queryWrapper = new QueryWrapper<OrderRating>()
                .eq("order_id", orderId)
                .eq("user_id", currentUserId);
        if (orderRatingMapper.selectCount(queryWrapper) > 0) {
            throw new RatingNotAllowedException("订单已评价");
        }
        OrderRating orderRating = OrderRating.builder()
                .orderId(orderId)
                .userId(currentUserId)
                .adminId(repairOrder.getAccpetedUser())
                .rating(orderRatingDTO.getRating())
                .comment(orderRatingDTO.getComment())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .createUser(currentUserId)
                .updateUser(currentUserId)
                .build();
        orderRatingMapper.insert(orderRating);
    }

    @Override
    public OrderRatingVO getRatingByOrderId(Long orderId) {
        RepairOrder repairOrder = repairOrderMapper.selectById(orderId);
        if (repairOrder == null) {
            throw new RatingNotAllowedException("订单不存在");
        }
        Long currentUserId = BaseContext.getCurrentId();
        if (!repairOrder.getUserId().equals(currentUserId)) {
            throw new RatingNotAllowedException("无权限查看该订单评价");
        }
        QueryWrapper<OrderRating> queryWrapper = new QueryWrapper<OrderRating>()
                .eq("order_id", orderId)
                .eq("user_id", currentUserId);
        OrderRating orderRating = orderRatingMapper.selectOne(queryWrapper);
        if (orderRating == null) {
            throw new RatingNotAllowedException("订单尚未评价");
        }
        OrderRatingVO orderRatingVO = new OrderRatingVO();
        BeanUtils.copyProperties(orderRating, orderRatingVO);
        return orderRatingVO;
    }

    @Override
    public PageResult getRatingsByAdmin(OrderSearchPageDTO orderSearchPageDTO) {
        Page<OrderRating> page = new Page<>(orderSearchPageDTO.getPage(), orderSearchPageDTO.getPageSize());
        QueryWrapper<OrderRating> queryWrapper = new QueryWrapper<OrderRating>()
                .eq("admin_id", BaseContext.getCurrentId())
                .orderByDesc("create_time");
        orderRatingMapper.selectPage(page, queryWrapper);
        List<OrderRating> records = page.getRecords();
        List<OrderRatingVO> list = new ArrayList<>();
        for (OrderRating record : records) {
            OrderRatingVO orderRatingVO = new OrderRatingVO();
            BeanUtils.copyProperties(record, orderRatingVO);
            if (record.getUserId() != null) {
                User user = userMapper.selectById(record.getUserId());
                if (user != null) {
                    orderRatingVO.setUserName(user.getName());
                }
            }
            list.add(orderRatingVO);
        }
        return new PageResult(page.getTotal(), list);
    }
}
