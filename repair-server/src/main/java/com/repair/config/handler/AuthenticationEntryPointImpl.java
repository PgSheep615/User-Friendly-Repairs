package com.repair.config.handler;

import com.alibaba.fastjson.JSON;
import com.repair.result.Result;
import com.repair.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理认证异常AuthenticationException
 * @author LZB
 * @date 2024/3/4
 * @Description
 */
@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("异常信息：{}", authException.getMessage());
        Result result = Result.error("用户认证失败请查询登录",HttpStatus.UNAUTHORIZED.value());
        String json = JSON.toJSONString(result);
        //处理异常
        WebUtils.renderString(response,json);
    }
}
