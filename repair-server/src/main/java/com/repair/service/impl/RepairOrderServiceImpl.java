package com.repair.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.math.LongMath;
import com.repair.constant.AdminCountConstant;
import com.repair.constant.RepairOrderAcceptedConstant;
import com.repair.context.BaseContext;
import com.repair.dto.OrderModifyDTO;
import com.repair.dto.OrderPageDTO;
import com.repair.dto.OrderSubmitDTO;
import com.repair.entity.Admin;
import com.repair.entity.RepairOrder;
import com.repair.mapper.AdminMapper;
import com.repair.mapper.RepairOrderMapper;
import com.repair.mapper.UserMapper;
import com.repair.result.PageResult;
import com.repair.service.RepairOrderService;
import com.repair.util.RedisCache;
import com.repair.vo.OrderCommunityVO;
import com.repair.vo.OrderHistoryVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private AdminMapper adminMapper;

    /**
     * 提交维修单并随机分配给管理员
     * @param orderSubmitDTO
     * @return
     */
    @Transactional
    public Long submitAndAccepted(OrderSubmitDTO orderSubmitDTO) {
        Long admincount = redisCache.getCacheObject(AdminCountConstant.AdminCount);
        if(admincount == null){
            admincount = adminMapper.selectCount(null);
            redisCache.setCacheObject(AdminCountConstant.AdminCount,admincount);
        }
        Long random = 0L;
        if(admincount > 0) {
            random = ThreadLocalRandom.current().nextLong(admincount);
        }
        Admin admin = adminMapper.selectByRandom(random);
        Long userId = null;
        if(admin != null){
            userId = admin.getUserId();
        }
        RepairOrder repairOrder = RepairOrder.builder()
                .userId(BaseContext.getCurrentId())
                .createTime(LocalDateTime.now())
                .createUser(BaseContext.getCurrentId())
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .isAccepted(RepairOrderAcceptedConstant.Accepted)
                .accpetedUser(userId)
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
        QueryWrapper<RepairOrder> queryWrapper = new QueryWrapper<RepairOrder>()
                .orderByDesc("create_time");
        repairOrderMapper.selectPage(page,queryWrapper);
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
                .eq("user_id",BaseContext.getCurrentId())
                .orderByDesc("create_time"); // 按 create_time 降序排序
        repairOrderMapper.selectPage(page,queryWrapper);
        List<RepairOrder> records = page.getRecords();
        List<OrderHistoryVO> list = new ArrayList<>();
        for (RepairOrder record : records) {
            OrderHistoryVO orderHistoryVO = new OrderHistoryVO();
            BeanUtils.copyProperties(record, orderHistoryVO);
            list.add(orderHistoryVO);
        }
        return new PageResult(page.getTotal(),list);
    }

    public PageResult myPageUtil(Page<RepairOrder> page){
        List<RepairOrder> records = page.getRecords();
        List<OrderCommunityVO> list = new ArrayList<>();
        for (RepairOrder record : records) {
            OrderCommunityVO orderCommunityVO = new OrderCommunityVO();
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
        repairOrder.setUpdateTime(LocalDateTime.now());
        repairOrder.setUpdateUser(BaseContext.getCurrentId());
        repairOrderMapper.updateById(repairOrder);
    }

    /**
     * 删除维修单
     * @param id
     */
    @Transactional
    public void delete(Long id) {
        RepairOrder repairOrder = repairOrderMapper.selectById(id);
        repairOrder.setUpdateUser(BaseContext.getCurrentId());
        repairOrder.setUpdateTime(LocalDateTime.now());
        repairOrder.setDeleteTime(LocalDateTime.now());
        repairOrder.setIsDeleted(1);
        repairOrderMapper.updateById(repairOrder);
    }

    @Override
    public void deleteById(Long id) {
        repairOrderMapper.deleteById(id);
    }
}




