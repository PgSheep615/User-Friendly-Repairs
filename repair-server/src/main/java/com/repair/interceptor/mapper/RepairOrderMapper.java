package com.repair.interceptor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.repair.annotation.AutoFill;
import com.repair.entity.RepairOrder;
import com.repair.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

/**
* @author LZB
* @description 针对表【repair_order(维修单信息表)】的数据库操作Mapper
* @createDate 2024-01-18 17:05:50
* @Entity com.repair.entity.RepairOrder
*/
@Mapper
public interface RepairOrderMapper extends BaseMapper<RepairOrder> {

    @AutoFill(OperationType.UPDATE)
    void updatePart(RepairOrder repairOrder);
}




