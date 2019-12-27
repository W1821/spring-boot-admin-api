package org.humki.baseadmin.common.util;


import org.apache.poi.ss.formula.functions.T;
import org.humki.baseadmin.common.constant.GlobalCodeEnum;
import org.humki.baseadmin.common.pojo.dto.base.message.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotNull;

/**
 * <br>
 * <b>功能：</b>消息工具类<br>

 * <b>日期：</b>2017/6/1 17:46<br>
 *
 * @author Kael
 */
public class ResponseMessageUtil {

    /* ================================================================================= */

    /**
     * 保存成功
     */
    public static ResponseMessage saveSuccess() {
        return success(GlobalCodeEnum.SuccessCode.SUCCESS_2010);
    }

    /**
     * 删除成功
     */
    public static ResponseMessage deleteSuccess() {
        return success(GlobalCodeEnum.SuccessCode.SUCCESS_2020);
    }

    /**
     * 参数id错误
     */
    public static ResponseMessage idError() {
        return error(GlobalCodeEnum.ErrorCode.ERROR_1031);
    }

    /**
     * 没有权限错误
     */
    public static ResponseMessage noAuthError() {
        return error(GlobalCodeEnum.ErrorCode.ERROR_401);
    }

    /* ================================================================================= */

    /**
     * 无权限
     */
    public static ResponseMessage buildNoAuthErrorMessage() {
        return ResponseMessage.builder()
                .errorCode(GlobalCodeEnum.ErrorCode.ERROR_401.getKey())
                .errorMsg(GlobalCodeEnum.ErrorCode.ERROR_401.getValue())
                .build();
    }

    /* ================================================================================= */

    /**
     * 系统出错
     */
    public static ResponseMessage error() {
        return ResponseMessage.builder()
                .errorCode(GlobalCodeEnum.ErrorCode.ERROR_500.getKey())
                .errorMsg(GlobalCodeEnum.ErrorCode.ERROR_500.getValue())
                .build();
    }

    /**
     * 系统出错
     */
    public static ResponseMessage error(String errorMsg) {
        return ResponseMessage.builder()
                .errorCode(GlobalCodeEnum.ErrorCode.ERROR_500.getKey())
                .errorMsg(errorMsg)
                .build();
    }

    /**
     * 错误提示信息
     */
    public static ResponseMessage error(@NotNull GlobalCodeEnum.ErrorCode errorCode) {
        return ResponseMessage.builder()
                .errorCode(errorCode.getKey())
                .errorMsg(errorCode.getValue())
                .build();
    }

    /**
     * 返回正确信息
     */
    public static ResponseMessage success() {
        return ResponseMessage.builder()
                .errorCode(GlobalCodeEnum.SuccessCode.SUCCESS.getKey())
                .errorMsg(GlobalCodeEnum.SuccessCode.SUCCESS.getValue())
                .build();
    }

    /**
     * 返回正确信息
     */
    public static ResponseMessage success(Object msg) {
        return ResponseMessage.builder()
                .errorCode(GlobalCodeEnum.SuccessCode.SUCCESS.getKey())
                .errorMsg(GlobalCodeEnum.SuccessCode.SUCCESS.getValue())
                .data(msg)
                .build();
    }

    /**
     * 返回正确信息
     */
    public static ResponseMessage success(@NotNull GlobalCodeEnum.SuccessCode successCode) {
        return ResponseMessage.builder()
                .errorCode(successCode.getKey())
                .errorMsg(successCode.getValue())
                .build();
    }

    /**
     * 返回正确信息
     */
    public static ResponseMessage success(GlobalCodeEnum.SuccessCode successCode, Object data) {
        return ResponseMessage.builder()
                .errorCode(successCode.getKey())
                .errorMsg(successCode.getValue())
                .data(data)
                .build();
    }

    /**
     * 创建响应对象
     */
    public static ResponseEntity<ResponseMessage<T>> createResponseEntity(ResponseMessage<T> message) {
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * 创建响应对象
     */
    public static ResponseEntity createResponseEntity(Object message) {
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


}
