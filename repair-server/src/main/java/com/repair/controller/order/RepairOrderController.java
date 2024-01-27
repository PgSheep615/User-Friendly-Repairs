package com.repair.controller.order;

import com.repair.dto.OrderModifyDTO;
import com.repair.dto.OrderPageDTO;
import com.repair.dto.OrderSubmitDTO;
import com.repair.result.PageResult;
import com.repair.result.Result;
import com.repair.service.RepairOrderService;
import com.repair.vo.OrderCommunityVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

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
    public Result submit(@RequestBody OrderSubmitDTO orderSubmitDTO){
        log.info("提交维修单{}",orderSubmitDTO);
        Long id = repairOrderService.submit(orderSubmitDTO);
        return Result.success(id);
    }

    @GetMapping("/community")
    @ApiOperation("获取社区列表")
    public Result<PageResult> allPage(OrderPageDTO orderPageDTO){
        log.info("获取社区列表{}",orderPageDTO);
        PageResult pageResult = repairOrderService.allPage(orderPageDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/")
    @ApiOperation("查询用户历史订单")
    public Result<PageResult> userPage(OrderPageDTO orderPageDTO){
        log.info("查询用户历史订单{}",orderPageDTO);
        PageResult pageResult = repairOrderService.userPage(orderPageDTO);
        return Result.success(pageResult);
    }

    @PutMapping("/modify")
    @ApiOperation("根据维修单id修改维修单信息")
    public Result modify(@RequestBody OrderModifyDTO orderModifyDTO){
        log.info("根据维修单id修改维修单信息{}",orderModifyDTO);
        repairOrderService.modify(orderModifyDTO);
        return Result.success();
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除维修单")
    //TODO @RequestParam
    public Result delete(@RequestParam("id") Long id){
        log.info("删除维修单{}",id);
        repairOrderService.delete(id);
        return Result.success();
    }
}
