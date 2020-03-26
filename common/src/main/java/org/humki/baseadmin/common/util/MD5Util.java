package org.humki.baseadmin.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

@Slf4j
public class MD5Util {

    /**
     * 获取字符串md5
     */
    public static String getMD5(String message) {
        if (message == null) {
            return null;
        }
        try {
            return DigestUtils.md5DigestAsHex(message.getBytes());
        } catch (Exception e) {
            log.error("MD5 error");
            return null;
        }
    }

}
