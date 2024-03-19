package com.repair.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.repair.constant.AdminScoreConstant;
import com.repair.constant.JwtClaimsConstant;
import com.repair.constant.RedisConstant;
import com.repair.context.BaseContext;
import com.repair.dto.LoginUser;
import com.repair.dto.UserLoginDTO;
import com.repair.dto.UserModifyDTO;
import com.repair.entity.Admin;
import com.repair.entity.User;
import com.repair.exception.LoginFailedException;
import com.repair.mapper.UserMapper;
import com.repair.properties.JwtProperties;
import com.repair.properties.WeChatProperties;
import com.repair.service.AdminService;
import com.repair.service.UserService;
import com.repair.util.RedisCache;
import com.repair.utils.HttpClientUtil;
import com.repair.utils.JwtUtil;
import com.repair.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
* @author LZB
* @description 针对表【user(用户信息表)】的数据库操作Service实现
* @createDate 2024-01-18 17:05:54
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    private final String url = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     *微信登陆
     * @param userLoginDTO
     * @return
     */
    @Override
    @Transactional
    public UserLoginVO wxLogin(UserLoginDTO userLoginDTO) {
        String openid = getOpenid(userLoginDTO.getCode());

        if(openid == null|| openid.equals("")){
            throw new LoginFailedException("微信用户登录失败！");
        }

        //判断当前用户是否为新用户
        QueryWrapper<User>
                queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid",openid);
        User user = userMapper.selectOne(queryWrapper);

        //如果是新用户，自动完成注册功能
        if(user==null){
            user = User.builder()
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .openid(openid)
                    .image("http://skyshow.oss-cn-hangzhou.aliyuncs.com/b6a3ad80-0d3c-4da8-815a-264c93e40fdb.jpg")
                    .build();
            userMapper.insert(user);
        }

        //为微信用户生成jwt令牌
        Map<String, Object> claim = new HashMap<>();
        claim.put(JwtClaimsConstant.USER_ID,user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(),jwtProperties.getUserTtl(),claim);
        log.info("jwt&token:{}",token);
        Integer isAdmin = 0;
        Admin admin = adminService.getOne(new QueryWrapper<Admin>().eq("user_id", user.getId()));
        List<String> permissions = new ArrayList<>();
        if(admin != null){
            isAdmin = 1;
            permissions.add("admin");
            ZSetOperations zSetOperations = redisTemplate.opsForZSet();
            Set range = zSetOperations.range(AdminScoreConstant.AdminScore, 0, -1);
            if (!range.contains(user.getId()) ){
                zSetOperations.add(AdminScoreConstant.AdminScore,user.getId(),admin.getScore());
            }
        }else{
            permissions.add("user");

        }
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .isAdmin(isAdmin)
                .build();
        // 把全部数据封装为LoginUser存入redis  方便后续权限的管理
        LoginUser loginUser = new LoginUser(user,permissions);

        //封装权限
        String key = RedisConstant.RedisGlobalKey +user.getId();
        redisCache.setCacheObject(key,loginUser,1,TimeUnit.DAYS);

        //返回这个用户对象
        return userLoginVO;
    }

    /*private LoginUser createLoginUser(User user) {
        List<String> permissions = new ArrayList<>();
        permissions.add(user.getPermission());
        String openid = user.getOpenid();
        user = new User();
        user.setOpenid(openid);
        return new LoginUser(user, permissions);
    }*/

    private String getOpenid(String code){
        //调用微信接口服务，获取当前微信的openid
        Map<String, String> map = new HashMap<>();
        map.put("appid",weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        String json = HttpClientUtil.doGet(url, map);

        //判断openid是否为空，空则标识登录失败，抛出异常
        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;
    }

    /**
     * 修改用户个人信息
     * @param userModifyDTO
     */
    @Override
    public void modify(UserModifyDTO userModifyDTO) {
        User user = new User();
        BeanUtils.copyProperties(userModifyDTO,user);
        userMapper.updateById(user);
    }

}




