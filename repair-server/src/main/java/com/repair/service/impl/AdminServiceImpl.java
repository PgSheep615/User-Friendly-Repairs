package com.repair.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.repair.context.BaseContext;
import com.repair.dto.AdminAddDTO;
import com.repair.dto.AdminSearchPageDTO;
import com.repair.dto.FeedbackSearchPageDTO;
import com.repair.dto.UserSearchPageDTO;
import com.repair.entity.Admin;
import com.repair.entity.Feedback;
import com.repair.entity.RepairOrder;
import com.repair.entity.User;
import com.repair.interceptor.mapper.AdminMapper;
import com.repair.interceptor.mapper.FeedbackMapper;
import com.repair.interceptor.mapper.RepairOrderMapper;
import com.repair.interceptor.mapper.UserMapper;
import com.repair.result.PageResult;
import com.repair.service.AdminService;
import com.repair.vo.AdminSearchVO;
import com.repair.vo.FeedbackSearchVO;
import com.repair.vo.UserSearchVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    /**
     * 添加管理员
     * @param adminAddDTO
     */
    public void add(AdminAddDTO adminAddDTO) {
        Admin admin = Admin.builder()
                .createTime(LocalDateTime.now())
                .createUser(BaseContext.getCurrentId())
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();
        BeanUtils.copyProperties(adminAddDTO,admin);
        adminMapper.insert(admin);
    }

    /**
     * 分页查询所有管理员
     * @param adminSearchPageDTO
     * @return
     */
    public PageResult pageAdmin(AdminSearchPageDTO adminSearchPageDTO) {
        Page<Admin> page = new Page<>(adminSearchPageDTO.getPage(),adminSearchPageDTO.getPageSize());
        adminMapper.selectPage(page,null);
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
    public void deleteAdmin(Long id) {
        Admin admin = adminMapper.selectById(id);
        admin.setDeleteTime(LocalDateTime.now());
        admin.setUpdateTime(LocalDateTime.now());
        admin.setUpdateUser(BaseContext.getCurrentId());
        adminMapper.updateById(admin);
        adminMapper.deleteById(id);
    }

    /**
     * 分页查询所有反馈信息
     * @param feedbackSearchPageDTO
     * @return
     */
    public PageResult pageFeedback(FeedbackSearchPageDTO feedbackSearchPageDTO) {
        Page<Feedback> page = new Page<>(feedbackSearchPageDTO.getPage(), feedbackSearchPageDTO.getPageSize());
        feedbackMapper.selectPage(page,null);
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
    public void accept(Long id) {
        RepairOrder repairOrder = repairOrderMapper.selectById(id);
        repairOrder.setIsAccepted(1);
        repairOrderMapper.updateById(repairOrder);
    }

    /**
     * 删除用户
     * @param id
     */
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




