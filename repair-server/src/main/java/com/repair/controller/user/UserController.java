package com.repair.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.repair.constant.JwtClaimsConstant;
import com.repair.dto.FeedbackDTO;
import com.repair.dto.UserLoginDTO;
import com.repair.dto.UserModifyDTO;
import com.repair.entity.Admin;
import com.repair.entity.User;
import com.repair.properties.JwtProperties;
import com.repair.result.Result;
import com.repair.service.AdminService;
import com.repair.service.FeedbackService;
import com.repair.service.UserService;
import com.repair.utils.JwtUtil;
import com.repair.vo.UserLoginVO;
import com.repair.vo.UserSearchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/user")
@Api(tags = "C端用户接口")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FeedbackService feedbackService;


    /**
     * 微信用户登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("微信用户登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        log.info("微信登陆{}",userLoginDTO.getCode());

        UserLoginVO userLoginVO = userService.wxLogin(userLoginDTO);


        return Result.success(userLoginVO);
    }
    @PutMapping("/modify")
    @ApiOperation("修改用户信息")
    @PreAuthorize("hasAnyAuthority('user','admin')")
    @CacheEvict(cacheNames = "userCache",key = "#userModifyDTO.id")
    public Result modify(@RequestBody UserModifyDTO userModifyDTO){
        log.info("修改用户信息{}",userModifyDTO);
        userService.modify(userModifyDTO);
        return Result.success();
    }

    @PostMapping("/feedback")
    @ApiOperation("提交用户反馈")
    @PreAuthorize("hasAnyAuthority('user','admin')")
    public Result feedback(@RequestBody FeedbackDTO feedbackDTO){
        log.info("用户反馈{}",feedbackDTO);
        feedbackService.feedback(feedbackDTO);
        return Result.success();
    }

    @GetMapping("{id}")
    @ApiOperation("根据用户id获取用户信息")
    @Cacheable(cacheNames = "userCache",key = "#id")
    @PreAuthorize("hasAnyAuthority('user','admin')")
    public Result<UserSearchVO> getById(@PathVariable Long id){
        User user = userService.getById(id);
        UserSearchVO userSearchVO = new UserSearchVO();
        BeanUtils.copyProperties(user,userSearchVO);
        return Result.success(userSearchVO);
    }
}
