package org.humki.baseadmin.base.service;

import lombok.extern.slf4j.Slf4j;
import org.humki.baseadmin.base.pojo.bo.UserBO;
import org.humki.baseadmin.base.pojo.dto.user.UserDTO;
import org.humki.baseadmin.base.pojo.po.UserModel;
import org.humki.baseadmin.common.constant.GlobalCodeEnum;
import org.humki.baseadmin.common.constant.GlobalEnum;
import org.humki.baseadmin.common.pojo.dto.base.message.EmptyData;
import org.humki.baseadmin.common.pojo.dto.base.message.ResponseMessage;
import org.humki.baseadmin.common.util.ResponseMessageUtil;
import org.springframework.stereotype.Service;

/**
 * 登录服务
 *
 * @author Kael
 */
@Slf4j
@Service
public class LoginService extends BaseBaseService {

    /**
     * 登录
     *
     * @return 用户信息
     */
    public ResponseMessage login() {
        log.debug("-----------------登录开始----------------");
        UserModel user = getUserDetail();
        // 帐号被禁用
        if (user.getAccountStatus() == null || user.getAccountStatus() == GlobalEnum.DISABLED.YES.getKey()) {
            log.debug("-----------------用户帐号禁用----------------");
            return ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_1011);
        }

        UserBO userBO = UserBO.builder().model(user).build();
        UserDTO userDTO = userBO.poToDto();
        log.debug("-----------------登录结束----------------");
        return ResponseMessageUtil.success(userDTO);
    }

}
