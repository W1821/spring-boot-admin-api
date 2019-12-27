package org.humki.baseadmin.common.pojo;

import org.humki.baseadmin.common.util.GsonUtil;

import java.io.Serializable;

/**
 * 基础的
 *
 * @author Kael
 */
public class BaseBean implements Serializable {
    @Override
    public String toString() {
        return GsonUtil.objToJsonString(this);
    }
}
