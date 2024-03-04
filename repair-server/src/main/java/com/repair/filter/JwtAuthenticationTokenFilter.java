package com.repair.filter;

import com.repair.constant.JwtClaimsConstant;
import com.repair.constant.RedisConstant;
import com.repair.context.BaseContext;
import com.repair.dto.LoginUser;
import com.repair.properties.JwtProperties;
import com.repair.util.RedisCache;
import com.repair.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author LZB
 * @date 2024/3/4
 * @Description
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException,
            IOException {
        //获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            //放行,之后会走到其他过滤器
            filterChain.doFilter(request, response);
            //后面过滤器执行完会回来，为了不执行后面的代码
            return;
        }
        //解析token
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(),token);
            //TODO claims为什么有两个参数,为什么取数据异常？？？Long不能强转String
            userId = "" + claims.get(JwtClaimsConstant.USER_ID);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        //从redis中获取用户信息
        String redisKey = RedisConstant.RedisGlobalKey + userId;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("用户未登录");
        }
        BaseContext.setCurrentId(Long.parseLong(userId));
        //存入SecurityContextHolder
        //TODO 获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                //传3个参数的，因为源码中给认证设为true了
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行，经过下一次过滤器
        filterChain.doFilter(request, response);
    }
}
