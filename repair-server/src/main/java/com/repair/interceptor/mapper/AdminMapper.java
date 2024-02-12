package com.repair.interceptor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.repair.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

/**
* @author LZB
* @description 针对表【admin(管理员信息表)】的数据库操作Mapper
* @createDate 2024-01-18 17:06:13
* @Entity com.repair.entity.Admin
*/
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

}



