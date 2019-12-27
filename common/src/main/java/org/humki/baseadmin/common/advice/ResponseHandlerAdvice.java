package org.humki.baseadmin.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * <br>
 * <b>功能：</b>返回结果增强处理<br>

 * <b>日期：</b>2017/4/11 23:27<br>
 *
 * @author Kael
 */
@Slf4j
@ControllerAdvice
public class ResponseHandlerAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 判断支持的类型
     */
    @Override
    public boolean supports(
            @Nullable MethodParameter returnType,
            @Nullable Class<? extends HttpMessageConverter<?>> converterType) {
        //返回true，执行beforeBodyWrite方法
        return true;
    }

    /**
     * 可以对响应体进行处理
     */
    @Override
    public Object beforeBodyWrite(Object body,
                                  @Nullable MethodParameter returnType,
                                  @Nullable MediaType selectedContentType,
                                  @Nullable Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @Nullable ServerHttpRequest request,
                                  @Nullable ServerHttpResponse response) {
        return body;
    }
}
