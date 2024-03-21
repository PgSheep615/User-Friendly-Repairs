package com.repair.config.handler;

import com.alibaba.fastjson.JSON;
import com.repair.result.Result;
import com.repair.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理授权异常AccessDeniedException
 * @author LZB
 * @date 2024/3/4
 * @Description
 */
@Component
@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("异常信息：{}", accessDeniedException.getMessage());
        Result result = Result.error("您的权限不足，如因系统出错请重新登陆", HttpStatus.FORBIDDEN.value());
        String json = JSON.toJSONString(result);
        //处理异常
        WebUtils.renderString(response,json);
    }
}
