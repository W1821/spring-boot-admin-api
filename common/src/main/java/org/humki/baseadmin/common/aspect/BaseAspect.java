package org.humki.baseadmin.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.humki.baseadmin.common.util.GsonUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 记录日志
 *
 * @author Kael
 * @date 2018/11/1 0001
 */
@Slf4j
public class BaseAspect {

    protected Object doAroundBase(ProceedingJoinPoint proceedingJoinPoint, Long userId) throws Throwable {

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (attributes != null) {
            request = attributes.getRequest();
        }

        long startTime = System.currentTimeMillis();
        // ob 为方法的返回值
        Object result = proceedingJoinPoint.proceed();

        // 记录下请求内容
        log.info("耗时 ={}ms, userId={}, ip={}, method={}, params={}, response={}",
                System.currentTimeMillis() - startTime,
                userId,
                request == null ? "未知" : request.getRemoteAddr(),
                proceedingJoinPoint.getSignature().toString(),
                Arrays.stream(proceedingJoinPoint.getArgs()).map(GsonUtil::objToJsonString).collect(Collectors.toList()),
                GsonUtil.objToJsonString(result));
        return result;

    }

}
