package org.humki.baseadmin.common.exception;

import lombok.Getter;

import org.humki.baseadmin.common.constant.GlobalCodeEnum;
import org.humki.baseadmin.common.pojo.dto.base.message.ResponseMessage;

/**
 * <br>
 * <b>功能：</b>全局异常处理<br>
 * <b>日期：</b>2017/4/11 23:27<br>
 *
 * @author Kael
 */
@Getter
public class AdminException extends RuntimeException {

    private ResponseMessage responseMessage;

    public AdminException(GlobalCodeEnum.ErrorCode errorCode) {
        this.responseMessage = ResponseMessage.builder()
                .errorCode(errorCode.getKey())
                .errorMsg(errorCode.getValue())
                .build();
    }

    /**
     * 重写，减少性能开销
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}
