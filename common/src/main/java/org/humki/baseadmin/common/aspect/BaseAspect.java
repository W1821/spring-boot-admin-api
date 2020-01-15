package org.humki.baseadmin.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.humki.baseadmin.common.util.FastJsonUtil;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    private final static long THRESHOLD = 2000L;    // 耗时接口

    private final static String ACCEPT = "Accept";

    private final static String JSON_ACCEPT = "application/json";

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

        long consumedTime = System.currentTimeMillis() - startTime;

        if (consumedTime > THRESHOLD) {
            log.warn("耗时操作警告 ={}ms, userId={}, ip={}, method={}",
                    consumedTime,
                    userId,
                    proceedingJoinPoint.getSignature().toString(),
                    request == null ? "未知" : request.getRemoteAddr());
        }
        // 记录下请求内容
        if (acceptIsJson(request)) {
            // json请求支持响应日志
            log.info("耗时 ={}ms, userId={}, ip={}, method={}, params={}, response={}",
                    consumedTime,
                    userId,
                    request.getRemoteAddr(),
                    proceedingJoinPoint.getSignature().toString(),
                    Arrays.stream(proceedingJoinPoint.getArgs())
                            .filter(arg -> !(arg instanceof HttpServletRequest))
                            .filter(arg -> !(arg instanceof HttpServletResponse))
                            .filter(arg -> !(arg instanceof Model))
                            .map(FastJsonUtil::objToJsonString)
                            .collect(Collectors.toList()),
                    FastJsonUtil.objToJsonString(result));
        } else {
            log.info("耗时 ={}ms, userId={}, ip={}, method={}, params={}",
                    consumedTime,
                    userId,
                    request == null ? "未知" : request.getRemoteAddr(),
                    proceedingJoinPoint.getSignature().toString(),
                    Arrays.stream(proceedingJoinPoint.getArgs())
                            .filter(arg -> !(arg instanceof HttpServletRequest))
                            .filter(arg -> !(arg instanceof HttpServletResponse))
                            .filter(arg -> !(arg instanceof Model))
                            .map(FastJsonUtil::objToJsonString)
                            .collect(Collectors.toList()));
        }
        return result;

    }

    /**
     * 判断是否是json请求
     */
    private boolean acceptIsJson(HttpServletRequest request) {
        if (request == null) {
            return false;
        }
        String accept = request.getHeader(ACCEPT);
        return JSON_ACCEPT.equals(accept);
    }

}
