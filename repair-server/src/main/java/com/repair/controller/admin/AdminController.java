package com.repair.controller.admin;

import com.repair.dto.*;
import com.repair.result.PageResult;
import com.repair.result.Result;
import com.repair.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

/**
 * @author LZB
 * @date 2024/1/27
 * @Description
 */
@RestController
@RequestMapping("user/admin")
@Slf4j
@Api(tags = "管理员相关接口")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/add")
    @ApiOperation("添加管理员")
    @PreAuthorize("hasAuthority('admin')")
    public Result add(@RequestBody AdminAddDTO adminAddDTO) {
        log.info("添加管理员:{}", adminAddDTO);
        adminService.add(adminAddDTO);
        return Result.success();
    }

    @GetMapping("/searchAdmin")
    @ApiOperation("根据条件分页查询所有管理员")
    @PreAuthorize("hasAnyAuthority('user','admin')")
    public Result<PageResult> pageAdmin(AdminSearchPageDTO adminSearchPageDTO) {
        log.info("查询所有管理员:{}", adminSearchPageDTO);
        PageResult pageResult = adminService.pageAdmin(adminSearchPageDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping("/deleteAdmin")
    @ApiOperation("删除管理员")
    @PreAuthorize("hasAuthority('admin')")
    public Result deleteAdmin(@RequestParam("id") Long id) {
        log.info("删除管理员:{}", id);
        adminService.deleteAdmin(id);
        return Result.success();
    }

    @GetMapping("/feedback")
    @ApiOperation("查询所有反馈")
    @PreAuthorize("hasAuthority('admin')")
    public Result<PageResult> pageFeedback(FeedbackSearchPageDTO feedbackSearchPageDTO) {
        log.info("查询所有反馈:{}", feedbackSearchPageDTO);
        PageResult pageResult = adminService.pageFeedback(feedbackSearchPageDTO);
        return Result.success(pageResult);
    }

    @PutMapping("/accept")
    @ApiOperation("接单")
    @PreAuthorize("hasAuthority('admin')")
    public Result accept(@RequestBody OrderAcceptDTO orderAcceptDTO) {
        log.info("接单{}", orderAcceptDTO);
        adminService.accept(orderAcceptDTO.getId());
        return Result.success();
    }

    @DeleteMapping("/deleteUser")
    @ApiOperation("删除用户")
    @PreAuthorize("hasAuthority('admin')")
    public Result deleteUser(@RequestParam("id") Long id) {
        log.info("删除用户:{}", id);
        adminService.deleteUser(id);
        return Result.success();
    }

    @GetMapping("/searchUser")
    @ApiOperation("分页查询所有用户")
    @PreAuthorize("hasAuthority('admin')")
    public Result<PageResult> pageUser(UserSearchPageDTO userSearchPageDTO) {
        log.info("分页查询所有用户");
        PageResult pageResult = adminService.pageUser(userSearchPageDTO);
        return Result.success(pageResult);
    }

    //TODO 查询管理员所被分配的维修单
    @GetMapping("/searchAccepted")
    @ApiOperation("分页查询查询管理员所被分配的维修单")
    @PreAuthorize("hasAuthority('admin')")
    public Result<PageResult> pageOrder(OrderSearchPageDTO orderSearchPageDTO) {
        log.info("分页查询查询管理员所被分配的维修单");
        PageResult pageResult = adminService.pageOrder(orderSearchPageDTO);
        return Result.success(pageResult);
    }
}
