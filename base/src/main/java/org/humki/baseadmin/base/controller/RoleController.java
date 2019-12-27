package org.humki.baseadmin.base.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.humki.baseadmin.common.constant.GlobalCodeEnum;
import org.humki.baseadmin.common.pojo.dto.base.message.ResponseMessage;
import org.humki.baseadmin.common.util.ResponseMessageUtil;
import org.humki.baseadmin.base.pojo.dto.role.RoleDTO;
import org.humki.baseadmin.base.pojo.dto.role.RoleSearchDTO;
import org.humki.baseadmin.base.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <br>
 * <b>功能：</b>系统角色<br>
 * <b>日期：</b>2017/4/11 23:27<br>
 *
 * @author Kael
 */
@Api("系统角色控制器")
@RestController
@RequestMapping("/role")
public class RoleController extends SystemBaseController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation(value = "列表数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseMessage list(@Valid @RequestBody RoleSearchDTO dto) {
        return roleService.list(dto);
    }

    @ApiOperation(value = "查询一个")
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public ResponseMessage query(@PathVariable("id") Long id) {
        return roleService.query(id);
    }

    @ApiOperation(value = "增加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseMessage add(@Valid @RequestBody RoleDTO dto) {
        if (dto.getId() != null) {
            return ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_1031);
        }
        if (StringUtils.isEmpty(dto.getRoleName())) {
            return ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_1032);
        }
        return roleService.save(dto);
    }

    @ApiOperation(value = "更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseMessage update(@Valid @RequestBody RoleDTO dto) {
        if (dto.getId() == null) {
            return ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_1031);
        }
        if (StringUtils.isEmpty(dto.getRoleName())) {
            return ResponseMessageUtil.error(GlobalCodeEnum.ErrorCode.ERROR_1032);
        }
        return roleService.save(dto);
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseMessage delete(@PathVariable("id") Long id) {
        return roleService.delete(id);
    }

    @ApiOperation(value = "获取角色列表数据")
    @RequestMapping(value = "/main/list", method = RequestMethod.GET)
    public ResponseMessage getRoleList() {
        return roleService.getRoleList();
    }
}
