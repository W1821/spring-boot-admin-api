package org.humki.baseadmin.security.config;

import org.humki.baseadmin.common.config.AdminConfig;
import org.humki.baseadmin.file.config.FileUploadConfig;
import org.humki.baseadmin.security.interceptor.MenuAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * <br>
 * <b>功能：</b>MVC配置<br>
 * 例如：public/image/** -> 磁盘某个位置<br>
 * 例如：拦截器<br>
 *
 * <b>日期：</b>2017/4/11 23:27<br>
 *
 * @author Kael
 */
@Configuration
@EnableConfigurationProperties(FileUploadConfig.class)
public class WebMvcConfig implements WebMvcConfigurer {

    private static final String FILE_START = "file:";

    private final AdminConfig adminConfig;
    private final SecurityConfig securityConfig;
    private final FileUploadConfig fileUploadConfig;
    private final MenuAuthInterceptor menuAuthInterceptor;

    @Autowired
    public WebMvcConfig(
            AdminConfig adminConfig,
            SecurityConfig securityConfig,
            FileUploadConfig fileUploadConfig,
            MenuAuthInterceptor menuAuthInterceptor) {
        this.adminConfig = adminConfig;
        this.securityConfig = securityConfig;
        this.fileUploadConfig = fileUploadConfig;
        this.menuAuthInterceptor = menuAuthInterceptor;
    }

    /**
     * 静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 要以文件分隔符结尾
        registry.addResourceHandler(fileUploadConfig.getResourceHandler())
                .addResourceLocations(getFileLocation(fileUploadConfig.getRootPath()));

    }

    private String getFileLocation(String rootPath) {
        String fileUploadLocation = FILE_START + rootPath;
        if (!fileUploadLocation.endsWith(File.separator)) {
            fileUploadLocation = fileUploadLocation + File.separator;
        }
        return fileUploadLocation;
    }

    /**
     * 拦截器配置
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 添加拦截规则
        // excludePathPatterns 排除拦截
        registry.addInterceptor(menuAuthInterceptor).addPathPatterns("/**")
                .excludePathPatterns(securityConfig.getLoginUrl())
                .excludePathPatterns(adminConfig.getIgnoredPath())
                .excludePathPatterns(fileUploadConfig.getResourceHandler());
    }

}
