package com.repair.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.repair.dto.FeedbackDTO;
import com.repair.entity.Feedback;

/**
* @author LZB
* @description 针对表【feedback(用户反馈信息表)】的数据库操作Service
* @createDate 2024-01-18 17:05:46
*/
public interface FeedbackService extends IService<Feedback> {

    void feedback(FeedbackDTO feedbackDTO);
}
