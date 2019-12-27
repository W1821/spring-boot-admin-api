package org.humki.baseadmin.security.config;

import lombok.Cleanup;
import org.humki.baseadmin.common.config.AdminConfig;
import org.humki.baseadmin.common.constant.GlobalConstant;
import org.humki.baseadmin.common.pojo.dto.base.message.ResponseMessage;
import org.humki.baseadmin.common.util.GsonUtil;
import org.humki.baseadmin.common.util.ResponseMessageUtil;
import org.humki.baseadmin.file.config.FileUploadConfig;
import org.humki.baseadmin.security.entrypoint.RestAuthenticationEntryPoint;
import org.humki.baseadmin.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <br>
 * <b>功能：</b>Security登录配置，HTTP基本认证(Basic Authentication)<br>
 *
 * <b>日期：</b>2017/4/11 23:27<br>
 * <p>
 * <br>@EnableGlobalMethodSecurity(prePostEnabled = true) //允许进入页面方法前检验
 *
 * @author Kael
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AdminConfig adminConfig;
    private final SecurityConfig securityConfig;
    private final FileUploadConfig fileUploadConfig;
    private final PasswordEncoder passwordEncoder;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public WebSecurityConfig(
            AdminConfig adminConfig,
            SecurityConfig securityConfig,
            FileUploadConfig fileUploadConfig,
            PasswordEncoder passwordEncoder,
            RestAuthenticationEntryPoint restAuthenticationEntryPoint,
            UserDetailsServiceImpl userDetailsService) {

        this.adminConfig = adminConfig;
        this.securityConfig = securityConfig;
        this.fileUploadConfig = fileUploadConfig;
        this.passwordEncoder = passwordEncoder;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //添加我们自定的user detail service认证；
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint)

                /* 登出请求配置 */
                .and()
                .logout()
                .logoutUrl(securityConfig.getLogoutUrl())
                .addLogoutHandler((request, response, authentication) -> this.logoutSuccess(response))

                /*  */
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()

                /*  */
                .and()
                .headers()
                .frameOptions()
                .disable()

                /* 开启跨域访问 */
                .and()
                .csrf()
                .disable()
                .cors();

    }

    private void logoutSuccess(HttpServletResponse response) {
        response.setStatus(HttpStatus.OK.value());
        response.setCharacterEncoding(GlobalConstant.CHARACTER_ENCODING_UTF8);
        response.setContentType(GlobalConstant.CONTENT_TYPE_APPLICATION_JSON);
        try {
            @Cleanup PrintWriter out = response.getWriter();
            ResponseMessage message = ResponseMessageUtil.success();
            out.append(GsonUtil.objToJsonString(message));
        } catch (IOException ignored) {
        }
    }

    @Override
    public void configure(WebSecurity web) {
        //忽略的路径，不会权限校验
        web.ignoring()
                .antMatchers(adminConfig.getIgnoredPath())
                .antMatchers(fileUploadConfig.getResourceHandler());
    }

}