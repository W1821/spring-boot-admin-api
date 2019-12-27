package org.humki.baseadmin.common.util;

import java.util.UUID;

/**
 * @author Kael
 * @date 2018/9/29 0029
 */
public class UUIDUtil {

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

}
