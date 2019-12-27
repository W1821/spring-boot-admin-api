package org.humki.baseadmin.base.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.humki.baseadmin.base.pojo.po.UserModel;
import org.humki.baseadmin.base.util.UserDetailUtil;
import org.humki.baseadmin.common.aspect.BaseAspect;
import org.springframework.context.annotation.Configuration;

/**
 * 记录日志
 *
 * @author Kael
 * @date 2018/11/1 0001
 */
@Slf4j
@Aspect
@Configuration
public class BaseWebLogAspect extends BaseAspect {

    @Pointcut("execution(public * org.humki.baseadmin.base.controller..*.*(..))")
    public void logPointCut() {
    }

    /**
     * 环绕通知
     */
    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 当前用户
        UserModel user = UserDetailUtil.getCurrentUser();
        Long id = user == null ? null: user.getId();
        return doAroundBase(proceedingJoinPoint, id);
    }

}
