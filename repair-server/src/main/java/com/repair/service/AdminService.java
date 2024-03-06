package com.repair.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.repair.dto.*;
import com.repair.entity.Admin;
import com.repair.result.PageResult;

/**
* @author LZB
* @description 针对表【admin(管理员信息表)】的数据库操作Service
* @createDate 2024-01-18 17:06:13
*/
public interface AdminService extends IService<Admin> {

    /**
     * 添加管理员
     * @param adminAddDTO
     */
    void add(AdminAddDTO adminAddDTO);

    /**
     * 分页查询所有管理员
     * @param adminSearchPageDTO
     * @return
     */
    PageResult pageAdmin(AdminSearchPageDTO adminSearchPageDTO);

    /**
     * 删除管理员
     * @param id
     */
    void deleteAdmin(Long id);

    /**
     * 分页查询所有反馈信息
     * @param feedbackSearchPageDTO
     * @return
     */
    PageResult pageFeedback(FeedbackSearchPageDTO feedbackSearchPageDTO);

    /**
     * 接单
     * @param id
     */
    void accept(Long id);

    /**
     * 删除用户
     * @param id
     */
    void deleteUser(Long id);

    /**
     * 分页查询所有用户
     * @return
     */
    PageResult pageUser(UserSearchPageDTO userSearchPageDTO);

    PageResult pageOrder(OrderSearchPageDTO orderSearchPageDTO);
}
