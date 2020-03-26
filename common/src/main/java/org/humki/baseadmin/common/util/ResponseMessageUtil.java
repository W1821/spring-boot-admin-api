package org.humki.baseadmin.common.util;


import org.humki.baseadmin.common.constant.GlobalCodeEnum;
import org.humki.baseadmin.common.pojo.dto.base.message.EmptyData;
import org.humki.baseadmin.common.pojo.dto.base.message.ResponseMessage;

import javax.validation.constraints.NotNull;

/**
 * <br>
 * <b>功能：</b>消息工具类<br>
 *
 * <b>日期：</b>2017/6/1 17:46<br>
 *
 * @author Kael
 */
public class ResponseMessageUtil {

    /* ========================================= success start======================================== */

    /**
     * 保存成功
     */
    public static ResponseMessage<EmptyData> saveSuccess() {
        return success(GlobalCodeEnum.SuccessCode.SUCCESS_2010);
    }

    /**
     * 删除成功
     */
    public static ResponseMessage<EmptyData> deleteSuccess() {
        return success(GlobalCodeEnum.SuccessCode.SUCCESS_2020);
    }

    /**
     * 返回正确信息
     */
    public static ResponseMessage<EmptyData> success() {
        return success(GlobalCodeEnum.SuccessCode.SUCCESS);
    }

    /**
     * 返回正确信息
     */
    public static ResponseMessage<EmptyData> success(@NotNull GlobalCodeEnum.SuccessCode successCode) {
        return ResponseMessage.<EmptyData>builder()
                .code(successCode.getKey())
                .msg(successCode.getValue())
                .build();
    }

    /**
     * 返回正确信息
     */
    public static <D> ResponseMessage<D> success(D data) {
        return success(GlobalCodeEnum.SuccessCode.SUCCESS, data);
    }

    /**
     * 返回正确信息
     */
    public static <D> ResponseMessage<D> success(GlobalCodeEnum.SuccessCode successCode, D data) {
        return ResponseMessage.<D>builder()
                .code(successCode.getKey())
                .msg(successCode.getValue())
                .data(data)
                .build();
    }

    /* ========================================= success end ======================================== */

    /* ====================================== error start=========================================== */

    /**
     * 参数错误：id无效！
     */
    public static ResponseMessage<EmptyData> idError() {
        return error(GlobalCodeEnum.ErrorCode.ERROR_1031);
    }

    /**
     * 无权限
     */
    public static ResponseMessage<EmptyData> error401() {
        return error(GlobalCodeEnum.ErrorCode.ERROR_401);
    }

    /**
     * 系统出错
     */
    public static ResponseMessage<EmptyData> error500() {
        return error(GlobalCodeEnum.ErrorCode.ERROR_500);
    }

    /**
     * 系统出错
     */
    public static ResponseMessage<EmptyData> error500(String errorMsg) {
        return ResponseMessage.<EmptyData>builder()
                .code(GlobalCodeEnum.ErrorCode.ERROR_500.getKey())
                .msg(errorMsg)
                .build();
    }

    /**
     * 错误提示信息
     */
    public static ResponseMessage<EmptyData> error(@NotNull GlobalCodeEnum.ErrorCode errorCode) {
        return ResponseMessage.<EmptyData>builder()
                .code(errorCode.getKey())
                .msg(errorCode.getValue())
                .build();
    }

    /* ====================================== error end=========================================== */


}
