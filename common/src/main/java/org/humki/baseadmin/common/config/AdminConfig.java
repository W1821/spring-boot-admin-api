package org.humki.baseadmin.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <br>
 * <b>功能：</b>基本配置<br>

 * <b>日期：</b>2017/4/11 23:27<br>
 *
 * @author Kael
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "admin.config")
public class AdminConfig {

    /**
     * 手机号码正则表达式
     */
    private String phoneNumberRegexp = "1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}";

    /**
     * 增加用户默认密码
     */
    private String defaultPassword = "admin123456";

    /**
     * 拦截器、权限验证忽略path
     */
    private String[] ignoredPath = {
            /* 静态资源 */
            "/",
            "/*.ico",
            "/*.js",
            "/*.css",
            "/*.html",
            "/assets/**",

            /* swagger页面，TODO 未做安全限制，项目部署注意一下 */
            "/webjars/**",
            "/v2/api-docs",
            "/swagger-resources/**",

            /* 前端访问防止404 */
            "/main/**",

            /* web socket TODO 暂时忽略，便于测试*/
            "/ws/**",

            /* 阿里数据库连接池监控 */
            "/druid/**"
    };

}
