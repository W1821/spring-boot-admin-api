package org.humki.baseadmin.security.entrypoint;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.humki.baseadmin.common.constant.GlobalConstant;
import org.humki.baseadmin.common.pojo.dto.base.message.ResponseMessage;
import org.humki.baseadmin.common.util.FastJsonUtil;
import org.humki.baseadmin.common.util.ResponseMessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 默认情况下, 由Spring Security提供的BasicAuthenticationEntryPoint 返回一个完整的401 Unauthorized 的html响应页面给客户端。
 * html格式在浏览器中很好的展示了错误信息，但是对其他情形却不太适合，
 * 比如对一个 REST API 来说，json 格式会更好。
 *
 * @author Kael
 */
@Slf4j
@Component
public class RestAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    /**
     * 认证密码错误提示
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding(GlobalConstant.CHARACTER_ENCODING_UTF8);
        response.setContentType(GlobalConstant.CONTENT_TYPE_APPLICATION_JSON);
        ResponseMessage message = ResponseMessageUtil.buildNoAuthErrorMessage();
        @Cleanup PrintWriter out = response.getWriter();
        out.append(FastJsonUtil.objToJsonString(message));
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName(GlobalConstant.REALM_NAME);
        super.afterPropertiesSet();
    }

}
