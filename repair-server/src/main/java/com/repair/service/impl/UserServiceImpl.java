package com.repair.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.repair.dto.UserLoginDTO;
import com.repair.dto.UserModifyDTO;
import com.repair.entity.User;
import com.repair.exception.LoginFailedException;
import com.repair.interceptor.mapper.UserMapper;
import com.repair.properties.WeChatProperties;
import com.repair.service.UserService;
import com.repair.utils.HttpClientUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
* @author LZB
* @description 针对表【user(用户信息表)】的数据库操作Service实现
* @createDate 2024-01-18 17:05:54
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    private final String url = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;




    /**
     *微信登陆
     * @param userLoginDTO
     * @return
     */
    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
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
                    .build();
            userMapper.insert(user);
        }
        //返回这个用户对象
        return user;
    }

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




