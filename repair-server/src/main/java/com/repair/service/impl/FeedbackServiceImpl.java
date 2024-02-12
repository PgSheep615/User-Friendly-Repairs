package com.repair.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.repair.context.BaseContext;
import com.repair.dto.FeedbackDTO;
import com.repair.entity.Feedback;
import com.repair.interceptor.mapper.FeedbackMapper;
import com.repair.service.FeedbackService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
* @author LZB
* @description 针对表【feedback(用户反馈信息表)】的数据库操作Service实现
* @createDate 2024-01-18 17:05:46
*/
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback>
    implements FeedbackService{

    @Autowired
    private  FeedbackMapper feedbackMapper;
    /**
     * 提交用户反馈
     * @param feedbackDTO
     */
    public void feedback(FeedbackDTO feedbackDTO) {
        Feedback feedback = Feedback.builder()
                .userId(BaseContext.getCurrentId())
                .createTime(LocalDateTime.now())
                .createUser(BaseContext.getCurrentId())
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();
        BeanUtils.copyProperties(feedbackDTO,feedback);
        feedbackMapper.insert(feedback);
    }
}




