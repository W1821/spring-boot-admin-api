package org.humki.baseadmin.security.config;

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
@ConfigurationProperties(prefix = "security.config")
public class SecurityConfig {

    /**
     * security的session退出请求
     */
    private String logoutUrl = "/system/logout";
    /**
     * 系统登录请求
     */
    private String loginUrl = "/system/login";


}
