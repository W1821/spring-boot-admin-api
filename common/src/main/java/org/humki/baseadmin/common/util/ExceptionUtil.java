package org.humki.baseadmin.common.util;

import org.humki.baseadmin.common.constant.GlobalCodeEnum;
import org.humki.baseadmin.common.exception.AdminException;

/**
 * <br>
 * <b>功能：</b>公共异常工具类<br>

 * <b>日期：</b>2017/6/1 17:46<br>
 *
 * @author Kael
 */
public class ExceptionUtil {

    /* =========================================================================== */

    /**
     * 抛出参数错误：id无效
     */
    public static void throwIdError() {
        throw new AdminException(GlobalCodeEnum.ErrorCode.ERROR_1031);
    }

    /**
     * 抛出参数错误：无权限
     */
    public static void throwUnauthorizedError() {
        throw new AdminException(GlobalCodeEnum.ErrorCode.ERROR_401);
    }

    /* =========================================================================== */

    /**
     * 抛出错误异常
     */
    public static void throwError(GlobalCodeEnum.ErrorCode errorCode) {
        throw new AdminException(errorCode);
    }


}
