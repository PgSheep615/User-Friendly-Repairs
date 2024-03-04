package com.repair.config;

import com.repair.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @author LZB
 * @date 2024/3/4
 * @Description
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfign extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//因为是前后端分离，关闭csrf,防范csrf攻击
                .csrf().disable()
//不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
// 对于登录接口 允许匿名访问
                //permitall是所有都能访问(静态资源放行），anonymous是未认证可以访问(登录接口），认证了之后就不能访问
                .antMatchers("/user/user/login").anonymous()
                .antMatchers("/doc.html#/**").permitAll()
                .antMatchers("/doc.html/**","/webjars/**", "/swagger-resources/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/doc.html/**", "/v2/api-docs/**","https://sentry.immersivetranslate.com/**").permitAll()
                ///////.antMatchers("**").permitAll()
                //静态资源
                .antMatchers("/*.svg","/*.png","/*.js","/*.css","/*.html").permitAll()
/////////////// .antMatchers("/testCors").hasAuthority("system:dept:list222")
// 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
        //把token校验过滤器添加到过滤器链中
        //TODO 不加在前面校验进不去》
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //配置异常处理器
        http.exceptionHandling()
                //配置认证失败处理器
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

        //允许跨域
        http.cors();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

