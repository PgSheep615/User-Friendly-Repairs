package com.repair.interceptor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.repair.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author LZB
* @description 针对表【user(用户信息表)】的数据库操作Mapper
* @createDate 2024-01-18 17:05:54
* @Entity com.repair.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




