package com.repair.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.repair.dto.FeedbackDTO;
import com.repair.dto.UserLoginDTO;
import com.repair.dto.UserModifyDTO;
import com.repair.entity.User;

/**
* @author LZB
* @description 针对表【user(用户信息表)】的数据库操作Service
* @createDate 2024-01-18 17:05:54
*/
public interface UserService extends IService<User> {

    /**
     * 微信用户登录
     * @return
     */
    User wxLogin(UserLoginDTO userLoginDTO);

    /**
     * 修改用户个人信息
     * @param userModifyDTO
     */
    void modify(UserModifyDTO userModifyDTO);

}
