package com.repair.controller.order;

import com.repair.dto.OrderModifyDTO;
import com.repair.dto.OrderPageDTO;
import com.repair.dto.OrderSubmitDTO;
import com.repair.entity.RepairOrder;
import com.repair.result.PageResult;
import com.repair.result.Result;
import com.repair.service.RepairOrderService;
import com.repair.vo.OrderHistoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author LZB
 * @date 2024/1/26
 * @Description
 */
@RequestMapping("/user/order")
@RestController
@Api(tags = "维修单接口")
@Slf4j
public class RepairOrderController {
    @Autowired
    private RepairOrderService  repairOrderService;

    @PostMapping("/submit")
    @ApiOperation("提交维修单")
    @PreAuthorize("hasAnyAuthority('user','admin')")
    public Result submit(@Valid  @RequestBody OrderSubmitDTO orderSubmitDTO){
        log.info("提交维修单{}",orderSubmitDTO);
        Long id = repairOrderService.submitAndAccepted(orderSubmitDTO);
        return Result.success(id);
    }

    @GetMapping("/community")
    @ApiOperation("获取社区列表")
    @PreAuthorize("hasAnyAuthority('user','admin')")
    public Result<PageResult> allPage(OrderPageDTO orderPageDTO){
        log.info("获取社区列表{}",orderPageDTO);
        PageResult pageResult = repairOrderService.allPage(orderPageDTO);
        return Result.success(pageResult);
    }
    @GetMapping("/historyOrder")
    @ApiOperation("查询用户历史维修单")
    @PreAuthorize("hasAnyAuthority('user','admin')")
    public Result<PageResult> userPage(OrderPageDTO orderPageDTO){
        log.info("查询用户历史维修单{}",orderPageDTO);
        PageResult pageResult = repairOrderService.userPage(orderPageDTO);
        return Result.success(pageResult);
    }

    @PutMapping("/modify")
    @ApiOperation("根据维修单id修改维修单信息")
    @CacheEvict(cacheNames = "orderCache",key = "#orderModifyDTO.id")
    @PreAuthorize("hasAnyAuthority('user','admin')")
    public Result modify(@RequestBody OrderModifyDTO orderModifyDTO){
        log.info("根据维修单id修改维修单信息{}",orderModifyDTO);
        repairOrderService.modify(orderModifyDTO);
        return Result.success();
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除维修单")
    @CacheEvict(cacheNames = "orderCache",key = "#id")
    @PreAuthorize("hasAnyAuthority('user','admin')")
    //TODO @RequestParam
    public Result delete(@RequestParam("id") Long id){
        log.info("删除维修单{}",id);
        repairOrderService.deleteById(id);
        return Result.success();
    }

    @GetMapping("{id}")
    @ApiOperation("根据id查询维修单详情")
    @Cacheable(cacheNames = "orderCache",key = "#id")
    @PreAuthorize("hasAnyAuthority('user','admin')")
    public Result<OrderHistoryVO> getById(@PathVariable Long id){
        RepairOrder order = repairOrderService.getById(id);
        OrderHistoryVO orderHistoryVO = new OrderHistoryVO();
        BeanUtils.copyProperties(order,orderHistoryVO);
        return Result.success(orderHistoryVO);
    }
}
