package org.humki.baseadmin.common.pojo.bo;

import org.humki.baseadmin.common.pojo.BaseBean;
import org.humki.baseadmin.common.pojo.dto.BaseDTO;
import org.humki.baseadmin.common.pojo.dto.base.message.ResponseMessage;
import org.humki.baseadmin.common.pojo.po.BasePO;

/**
 * @author Kael
 */
public abstract class BaseBO extends BaseBean {

    /* ================================================================================= */

    /**
     * 查询一条数据
     *
     * @return 统一响应对象
     */
    public abstract ResponseMessage queryOne();

    /**
     * 添加或更新
     *
     * @return 统一响应对象
     */
    public abstract ResponseMessage save();

    /**
     * 删除
     *
     * @return 统一响应对象
     */
    public abstract ResponseMessage delete();

    /**
     * 数据库对象转数据传输对象
     *
     * @return 数据传输对象
     */
    public abstract BaseDTO poToDto();

    /**
     * 数据传输对象转数据库对象
     *
     * @return 数据库对象
     */
    public abstract BasePO dtoToPo();

}
