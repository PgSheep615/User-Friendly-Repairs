package com.repair.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.repair.annotation.AutoFill;
import com.repair.entity.Admin;
import com.repair.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
* @author LZB
* @description 针对表【admin(管理员信息表)】的数据库操作Mapper
* @createDate 2024-01-18 17:06:13
* @Entity com.repair.entity.Admin
*/
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
    @Select("select * from admin where user_id = #{userId}")
    public Admin selectByUserId(Long userId);

    @AutoFill(OperationType.UPDATE)
    @Update("update admin set is_deleted = 0 , group_name = #{groupName} where id = #{id}")
    void addMyAdminById(Admin admin);

    @Select("select * from admin where is_deleted = 0 limit #{random},1 ;")
    Admin selectByRandom(Long random);
}




