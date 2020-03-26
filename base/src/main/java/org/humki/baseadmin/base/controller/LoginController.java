package org.humki.baseadmin.base.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.humki.baseadmin.base.pojo.dto.user.UserDTO;
import org.humki.baseadmin.base.service.LoginService;
import org.humki.baseadmin.common.constant.GlobalCodeEnum;
import org.humki.baseadmin.common.pojo.dto.base.message.EmptyData;
import org.humki.baseadmin.common.pojo.dto.base.message.ResponseMessage;
import org.humki.baseadmin.common.util.ResponseMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 登录、登出
 *
 * @author Kael
 */
@Api(tags = "登录、登出")
@RestController
@RequestMapping("/system")
public class LoginController extends BaseBaseController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * 1
     * $2a$10$Joh6UCftJL5rYwBxtd7qc.xu8ANntPrcHzp55NtWFWodFWAlOsi.G
     */
    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ResponseMessage<UserDTO> login() {
        return loginService.login();
    }


    @ApiOperation(value = "退出")
    @ApiResponse(code = 200, message = "退出成功", response = ResponseMessage.class)
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseMessage<EmptyData> logout(HttpSession session) {
        session.invalidate();
        return ResponseMessageUtil.success(GlobalCodeEnum.SuccessCode.SUCCESS_2002);
    }


}
