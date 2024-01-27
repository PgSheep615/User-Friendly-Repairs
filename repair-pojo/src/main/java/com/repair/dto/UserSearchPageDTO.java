package com.repair.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author LZB
 * @date 2024/1/27
 * @Description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchPageDTO {
    //页码
    private int page;

    //每页记录数
    private int pageSize;
}
