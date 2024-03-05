package com.repair.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.repair.constant.AdminCountConstant;
import com.repair.constant.RedisConstant;
import com.repair.context.BaseContext;
import com.repair.dto.*;
import com.repair.entity.Admin;
import com.repair.entity.Feedback;
import com.repair.entity.RepairOrder;
import com.repair.entity.User;
import com.repair.mapper.AdminMapper;
import com.repair.mapper.FeedbackMapper;
import com.repair.mapper.RepairOrderMapper;
import com.repair.mapper.UserMapper;
import com.repair.result.PageResult;
import com.repair.service.AdminService;
import com.repair.util.RedisCache;
import com.repair.vo.AdminSearchVO;
import com.repair.vo.FeedbackSearchVO;
import com.repair.vo.UserSearchVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
* @author LZB
* @description 针对表【admin(管理员信息表)】的数据库操作Service实现
* @createDate 2024-01-18 17:06:13
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FeedbackMapper feedbackMapper;
    @Autowired
    private RepairOrderMapper repairOrderMapper;
    @Autowired
    private RedisCache redisCache;

    /**
     * 修改reids的用户权限信息
     */
    private void alterRedisPermissions(Long userId,String permissions){
        String key = RedisConstant.RedisGlobalKey + userId;
        LoginUser loginUser = redisCache.getCacheObject(key);
        redisCache.deleteObject(key);
        List<String> permissionsList = new ArrayList<>();
        permissionsList.add(permissions);
        loginUser.setPermissions(permissionsList);
        redisCache.setCacheObject(key,loginUser,1, TimeUnit.DAYS);
    }

    private void add1RedisAdminCount(boolean add){
        Long adminCount = redisCache.getCacheObject(AdminCountConstant.AdminCount);
        redisCache.deleteObject(AdminCountConstant.AdminCount);
        if(add){
            redisCache.setCacheObject(AdminCountConstant.AdminCount,adminCount+1,1, TimeUnit.DAYS);
        }else{
            redisCache.setCacheObject(AdminCountConstant.AdminCount,adminCount-1,1, TimeUnit.DAYS);

        }
    }


    /**
     * 添加管理员
     * @param adminAddDTO
     */
    @Transactional
    public void add(AdminAddDTO adminAddDTO) {
        Admin admin = Admin.builder()
                .createTime(LocalDateTime.now())
                .createUser(BaseContext.getCurrentId())
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();
        BeanUtils.copyProperties(adminAddDTO,admin);
        Long AdminId = adminMapper.selectByUserId(adminAddDTO.getUserId());
        if (AdminId == null){
            adminMapper.insert(admin);
        }else{
            Admin exitsAdmin = new Admin();
            BeanUtils.copyProperties(adminAddDTO,exitsAdmin);
            exitsAdmin.setId(AdminId);
            adminMapper.addMyAdminById(exitsAdmin);
        }
        add1RedisAdminCount(true);
        alterRedisPermissions(adminAddDTO.getUserId(),"admin");
    }

    /**
     * 分页查询所有管理员
     * @param adminSearchPageDTO
     * @return
     */
    public PageResult pageAdmin(AdminSearchPageDTO adminSearchPageDTO) {
        Page<Admin> page = new Page<>(adminSearchPageDTO.getPage(),adminSearchPageDTO.getPageSize());
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<Admin>()
                .orderByDesc("create_time");
        adminMapper.selectPage(page,queryWrapper);
        List<Admin> records = page.getRecords();
        List<AdminSearchVO> list = new ArrayList<>();
        for (Admin record : records) {
            User user = userMapper.selectById(record.getUserId());
            AdminSearchVO adminSearchVO = AdminSearchVO.builder()
                    .name(user.getName())
                    .campus(user.getCampus())
                    .phoneNumber(user.getPhoneNumber())
                    .build();
            BeanUtils.copyProperties(record, adminSearchVO);
            list.add(adminSearchVO);
        }
        return new PageResult(page.getTotal(),list);
    }

    /**
     * 删除管理员
     * @param id
     */
    @Transactional
    public void deleteAdmin(Long id) {
        Admin admin = adminMapper.selectById(id);
        admin.setDeleteTime(LocalDateTime.now());
        admin.setUpdateTime(LocalDateTime.now());
        admin.setUpdateUser(BaseContext.getCurrentId());
        adminMapper.updateById(admin);
        adminMapper.deleteById(id);
        add1RedisAdminCount(false);
        alterRedisPermissions(admin.getUserId(),"user");
    }

    /**
     * 分页查询所有反馈信息
     * @param feedbackSearchPageDTO
     * @return
     */
    @Transactional
    public PageResult pageFeedback(FeedbackSearchPageDTO feedbackSearchPageDTO) {
        Page<Feedback> page = new Page<>(feedbackSearchPageDTO.getPage(), feedbackSearchPageDTO.getPageSize());
        QueryWrapper<Feedback> queryWrapper = new QueryWrapper<Feedback>()
                .orderByDesc("create_time");
        feedbackMapper.selectPage(page,queryWrapper);
        List<Feedback> records = page.getRecords();
        List<FeedbackSearchVO> list = new ArrayList<>();
        for (Feedback record : records) {
            User user =  userMapper.selectById(record.getUserId());
            FeedbackSearchVO feedbackSearchVO = FeedbackSearchVO.builder()
                    .name(user.getName())
                    .campus(user.getCampus())
                    .feedbackDescription(record.getFeedbackDescription())
                    .build();
            list.add(feedbackSearchVO);
        }
        return new PageResult(page.getTotal(), list);
    }

    /**
     * 接单
     * @param id
     */
    //TODO 标记接单的人
    public void accept(Long id) {
        RepairOrder repairOrder = repairOrderMapper.selectById(id);
        repairOrder.setIsAccepted(1);
        repairOrder.setUpdateUser(BaseContext.getCurrentId());
        repairOrder.setUpdateTime(LocalDateTime.now());
        repairOrder.setAccpetedUser(BaseContext.getCurrentId());
        repairOrderMapper.updateById(repairOrder);
    }

    /**
     * 删除用户
     * @param id
     */
    @Transactional
    public void deleteUser(Long id) {
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("user_id", id));
        if(admin != null) {
            deleteAdmin(admin.getId());
        }
        User user = userMapper.selectById(id);
        user.setUpdateTime(LocalDateTime.now());
        user.setUpdateUser(BaseContext.getCurrentId());
        user.setDeleteTime(LocalDateTime.now());
        userMapper.deleteById(id);
        redisCache.deleteObject(RedisConstant.RedisGlobalKey + user.getId());
    }

    /**
     * 分页查询所有用户
     * @return
     */
    public PageResult pageUser(UserSearchPageDTO userSearchPageDTO) {
        Page<User> page = new Page<>(userSearchPageDTO.getPage(), userSearchPageDTO.getPageSize());
        userMapper.selectPage(page,null);
        List<User> records = page.getRecords();
        List<UserSearchVO> list = new ArrayList<>();
        for (User record : records) {
            UserSearchVO userSearchVO = new UserSearchVO();
            BeanUtils.copyProperties(record, userSearchVO);
            list.add(userSearchVO);
        }
        return new PageResult(page.getTotal(), list);
    }
}




