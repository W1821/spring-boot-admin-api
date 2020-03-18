package org.humki.baseadmin;

import lombok.extern.slf4j.Slf4j;
import org.humki.baseadmin.common.util.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * {@link EnableAutoConfiguration} :启动定时器
 * {@link EnableCaching} :启动缓存
 *
 * @author Kael
 */
@Slf4j
@SpringBootApplication
@EnableScheduling
@EnableCaching
public class ServerApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(ServerApplication.class, args);
        SpringContextUtil.setApplicationContext(applicationContext);
    }

    @PostConstruct
    public void init() {
        log.info("应用启动");
    }

    @PreDestroy
    public void destroy() {
        log.info("应用关闭");
    }

}
