package com.repair.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.repair.context.BaseContext;
import com.repair.dto.OrderModifyDTO;
import com.repair.dto.OrderPageDTO;
import com.repair.dto.OrderSubmitDTO;
import com.repair.entity.RepairOrder;
import com.repair.entity.User;
import com.repair.mapper.RepairOrderMapper;
import com.repair.mapper.UserMapper;
import com.repair.result.PageResult;
import com.repair.service.RepairOrderService;
import com.repair.vo.OrderCommunityVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
* @author LZB
* @description 针对表【repair_order(维修单信息表)】的数据库操作Service实现
* @createDate 2024-01-18 17:05:50
*/
@Service
public class RepairOrderServiceImpl extends ServiceImpl<RepairOrderMapper, RepairOrder>
    implements RepairOrderService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RepairOrderMapper repairOrderMapper;
    /**
     * 提交维修单
     * @param orderSubmitDTO
     * @return
     */
    public Long submit(OrderSubmitDTO orderSubmitDTO) {
        RepairOrder repairOrder = RepairOrder.builder()
                .userId(BaseContext.getCurrentId())
                .createTime(LocalDateTime.now())
                .createUser(BaseContext.getCurrentId())
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();
        BeanUtils.copyProperties(orderSubmitDTO, repairOrder);
        repairOrderMapper.insert(repairOrder);
        return repairOrder.getId();
    }

    /**
     * 分页查询所有用户的维修单
     * @param orderPageDTO
     * @return
     */
    public PageResult allPage(OrderPageDTO orderPageDTO) {
        //TODO 此处必须RepairOrder为泛型？
        Page<RepairOrder> page = new Page<>(orderPageDTO.getPage(), orderPageDTO.getPageSize());
        repairOrderMapper.selectPage(page,null);
        return myPageUtil(page);
    }

    /**
     * 分类查询单个用户的历史维修单
     * @param orderPageDTO
     * @return
     */
    public PageResult userPage(OrderPageDTO orderPageDTO) {
        Page<RepairOrder> page = new Page<>(orderPageDTO.getPage(), orderPageDTO.getPageSize());
        QueryWrapper<RepairOrder> queryWrapper = new QueryWrapper<RepairOrder>()
                .eq("user_id",orderPageDTO.getId());
        repairOrderMapper.selectPage(page,queryWrapper);
        return myPageUtil(page);
    }

    public PageResult myPageUtil(Page<RepairOrder> page){
        List<RepairOrder> records = page.getRecords();
        List<OrderCommunityVO> list = new ArrayList<>();
        for (RepairOrder record : records) {
            User user = userMapper.selectById(record.getUserId());
            OrderCommunityVO orderCommunityVO = OrderCommunityVO.builder()
                    .name(user.getName())
                    .address(user.getAddress())
                    .build();
            BeanUtils.copyProperties(record, orderCommunityVO);
            list.add(orderCommunityVO);
        }
        return new PageResult(page.getTotal(),list);
    }

    /**
     * 修改维修单信息
     * @param orderModifyDTO
     */
    @Override
    public void modify(OrderModifyDTO orderModifyDTO) {
        RepairOrder repairOrder = new RepairOrder();
        BeanUtils.copyProperties(orderModifyDTO,repairOrder);
        repairOrderMapper.updatePart(repairOrder);
    }

    /**
     * 删除维修单
     * @param id
     */
    public void delete(Long id) {
        RepairOrder repairOrder = repairOrderMapper.selectById(id);
        repairOrder.setUpdateUser(BaseContext.getCurrentId());
        repairOrder.setUpdateTime(LocalDateTime.now());
        repairOrder.setDeleteTime(LocalDateTime.now());
        repairOrder.setIsDeleted(1);
        repairOrderMapper.updateById(repairOrder);
    }
}




