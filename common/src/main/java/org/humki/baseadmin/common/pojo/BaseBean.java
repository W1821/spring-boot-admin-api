package org.humki.baseadmin.common.pojo;

import org.humki.baseadmin.common.util.FastJsonUtil;

/**
 * @author Kael
 */
public class BaseBean {

    /**
     * 重写toString方法，json格式化
     *
     * @return FastJson格式化后的字符串
     */
    @Override
    public String toString() {
        return FastJsonUtil.objToJsonString(this);
    }
}
