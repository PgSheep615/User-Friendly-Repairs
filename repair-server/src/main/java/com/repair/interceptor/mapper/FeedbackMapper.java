package com.repair.interceptor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.repair.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;

/**
* @author LZB
* @description 针对表【feedback(用户反馈信息表)】的数据库操作Mapper
* @createDate 2024-01-18 17:05:46
* @Entity com.repair.entity.Feedback
*/
@Mapper
public interface FeedbackMapper extends BaseMapper<Feedback> {

}




