package com.repair.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.repair.dto.OrderModifyDTO;
import com.repair.dto.OrderPageDTO;
import com.repair.dto.OrderSubmitDTO;
import com.repair.entity.RepairOrder;
import com.repair.result.PageResult;

/**
* @author LZB
* @description 针对表【repair_order(维修单信息表)】的数据库操作Service
* @createDate 2024-01-18 17:05:50
*/
public interface RepairOrderService extends IService<RepairOrder> {

    /**
     * 提交维修单
     * @param orderSubmitDTO
     * @return
     */
    Long submit(OrderSubmitDTO orderSubmitDTO);

    /**
     * 分类查询所有用户的历史维修单
     * @param orderPageDTO
     * @return
     */
    PageResult allPage(OrderPageDTO orderPageDTO);

    /**
     * 分类查询单个用户的历史维修单
     * @param orderPageDTO
     * @return
     */
    PageResult userPage(OrderPageDTO orderPageDTO);

    PageResult myPageUtil(Page<RepairOrder> page);

    /**
     * 修改维修单
     * @param orderModifyDTO
     */
    void modify(OrderModifyDTO orderModifyDTO);

    void delete(Long id);

    void deleteById(Long id);
}
