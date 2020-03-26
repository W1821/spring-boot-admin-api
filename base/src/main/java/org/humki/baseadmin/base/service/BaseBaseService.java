package org.humki.baseadmin.base.service;


import org.humki.baseadmin.base.pojo.po.UserModel;
import org.humki.baseadmin.base.util.UserDetailUtil;
import org.humki.baseadmin.common.service.BaseService;

/**
 * 基本的service
 *
 * @author Kael
 */
public class BaseBaseService extends BaseService {

    /* ================================================================================= */

    /**
     * 当前登录用户
     */
    protected UserModel getUserDetail() {
        return UserDetailUtil.getCurrentUser();
    }


    /* ================================================================================= */

}
