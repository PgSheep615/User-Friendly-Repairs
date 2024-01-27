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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private AdminService adminService;
    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 微信用户登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("微信用户登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        log.info("微信登陆{}",userLoginDTO.getCode());

        User user = userService.wxLogin(userLoginDTO);

        //为微信用户生成jwt令牌
        Map<String, Object> claim = new HashMap<>();
        claim.put(JwtClaimsConstant.USER_ID,user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(),jwtProperties.getUserTtl(),claim);
        log.info("jwt&token:{}",token);
        Integer isAdmin = 0;
        Admin admin = adminService.getOne(new QueryWrapper<Admin>().eq("user_id", user.getId()));
        if(admin != null){
            isAdmin = 1;
        }
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .isAdmin(isAdmin)
                .build();
        return Result.success(userLoginVO);
    }
    @PutMapping("/modify")
    @ApiOperation("修改用户信息")
    public Result modify(@RequestBody UserModifyDTO userModifyDTO){
        log.info("修改用户信息{}",userModifyDTO);
        userService.modify(userModifyDTO);
        return Result.success();
    }

    @PostMapping("/feedback")
    @ApiOperation("提交用户反馈")
    public Result feedback(@RequestBody FeedbackDTO feedbackDTO){
        log.info("用户反馈{}",feedbackDTO);
        feedbackService.feedback(feedbackDTO);
        return Result.success();
    }
}