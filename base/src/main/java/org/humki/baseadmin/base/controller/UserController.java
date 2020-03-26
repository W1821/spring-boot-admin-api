package org.humki.baseadmin.base.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.humki.baseadmin.base.pojo.dto.user.ModifyPwdDTO;
import org.humki.baseadmin.base.pojo.dto.user.UserDTO;
import org.humki.baseadmin.base.pojo.dto.user.UserSearchDTO;
import org.humki.baseadmin.base.service.UserService;
import org.humki.baseadmin.common.config.AdminConfig;
import org.humki.baseadmin.common.constant.GlobalCodeEnum;
import org.humki.baseadmin.common.pojo.dto.base.message.EmptyData;
import org.humki.baseadmin.common.pojo.dto.base.message.ResponseMessage;
import org.humki.baseadmin.common.util.ResponseMessageUtil;
import org.humki.baseadmin.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <br>
 * <b>功能：</b>系统人员<br>
 *
 * <b>日期：</b>2017/4/11 23:27<br>
 *
 * @author Kael
 */
@Api(tags = "系统人员")
@RestController
@RequestMapping("/user")
public class UserController extends BaseBaseController {

    private final AdminConfig adminConfig;
    private final UserService userService;

    @Autowired
    public UserController(AdminConfig adminConfig, UserService userService) {
        this.adminConfig = adminConfig;
        this.userService = userService;
    }

    @ApiOperation(value = "列表数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseMessage<Page<UserDTO>> list(@RequestBody UserSearchDTO dto) {
        return userService.list(dto);
    }

    @ApiOperation(value = "查询一个")
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public ResponseMessage<UserDTO> query(@PathVariable("id") Long id) {
        return userService.query(id);
    }


    @ApiOperation(value = "增加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseMessage<EmptyData> add(@Valid @RequestBody UserDTO dto) {
        if (dto.getId() != null) {
            return ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_1031);
        }
        if (!dto.getPhoneNumber().matches(adminConfig.getPhoneNumberRegexp())) {
            return ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_1201);
        }
        return userService.save(dto);
    }

    @ApiOperation(value = "更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseMessage<EmptyData> update(@Valid @RequestBody UserDTO dto) {
        if (dto.getId() == null) {
            return ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_1031);
        }
        if (!dto.getPhoneNumber().matches(adminConfig.getPhoneNumberRegexp())) {
            return ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_1201);
        }
        return userService.save(dto);
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseMessage<EmptyData> delete(@PathVariable("id") Long id) {
        return userService.delete(id);
    }

    @ApiOperation(value = "修改密码")
    @RequestMapping(value = "/modify/ownPwd", method = RequestMethod.POST)
    public ResponseMessage<EmptyData> modifyOwnPassword(@Valid @RequestBody ModifyPwdDTO dto) {
        if (!StringUtil.equals(dto.getNewPwd(), dto.getVerifiedPwd())) {
            return ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_1051);
        }
        return userService.modifyOwnPassword(dto);
    }

    @ApiOperation(value = "判断手机号码是否重复")
    @RequestMapping(value = "/check/number/{phoneNumber}", method = RequestMethod.GET)
    public ResponseMessage<EmptyData> phoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        return userService.checkNumber(phoneNumber);
    }

}
