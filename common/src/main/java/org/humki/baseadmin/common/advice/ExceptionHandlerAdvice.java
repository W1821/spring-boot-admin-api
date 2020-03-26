package org.humki.baseadmin.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.humki.baseadmin.common.exception.AdminException;
import org.humki.baseadmin.common.pojo.dto.base.message.ResponseMessage;
import org.humki.baseadmin.common.util.FastJsonUtil;
import org.humki.baseadmin.common.util.ResponseMessageUtil;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <br>
 * <b>功能：</b>Controller异常增强处理<br>
 *
 * <b>日期：</b>2017/4/11 23:27<br>
 *
 * @author Kael
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * 全局异常捕捉处理
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseMessage errorHandler(Exception ex) {
        log.error("全局异常捕捉处理", ex);
        return ResponseMessageUtil.error500();
    }

    /**
     * 参数验证异常捕捉处理
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseMessage errorHandler(MethodArgumentNotValidException ex) {
        // 参数校验失败信息
        FieldError fieldError = ex.getBindingResult().getFieldError();

        log.error("拦截捕捉参数验证异常 - message = {}", fieldError);

        if (fieldError == null) {
            return ResponseMessageUtil.error500();
        } else {
            return ResponseMessageUtil.error500(fieldError.getDefaultMessage());
        }

    }

    /**
     * 拦截捕捉自定义异常
     */
    @ResponseBody
    @ExceptionHandler(value = AdminException.class)
    public ResponseMessage myErrorHandler(AdminException ex) {
        ResponseMessage responseMessage = ex.getResponseMessage();
        log.error("拦截捕捉自定义异常 - message = {}", FastJsonUtil.objToJsonString(responseMessage));
        return responseMessage;
    }


}
