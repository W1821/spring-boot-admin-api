package org.humki.baseadmin.base.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.humki.baseadmin.base.pojo.dto.menu.MenuDTO;
import org.humki.baseadmin.base.service.MenuService;
import org.humki.baseadmin.common.annotation.HandlerAuth;
import org.humki.baseadmin.common.pojo.dto.base.message.EmptyData;
import org.humki.baseadmin.common.pojo.dto.base.message.ResponseMessage;
import org.humki.baseadmin.common.util.ResponseMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <br>
 * <b>功能：</b>系统菜单<br>
 *
 * <b>日期：</b>2017/4/11 23:27<br>
 *
 * @author Kael
 */
@Api(tags = "系统菜单")
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseBaseController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @ApiOperation(value = "获取菜单列表数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseMessage<List<MenuDTO>> list() {
        return menuService.list();
    }

    @ApiOperation(value = "获取一个菜单数据")
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public ResponseMessage<MenuDTO> query(@PathVariable("id") Long id) {
        return menuService.query(id);
    }

    @ApiOperation(value = "增加一个菜单数据")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseMessage<EmptyData> add(@Valid @RequestBody MenuDTO dto) {
        if (dto.getId() != null) {
            return ResponseMessageUtil.idError();
        }
        return menuService.save(dto);
    }

    @ApiOperation(value = "修改一个菜单数据")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseMessage<EmptyData> update(@Valid @RequestBody MenuDTO dto) {
        if (dto.getId() == null) {
            return ResponseMessageUtil.idError();
        }
        return menuService.save(dto);
    }

    @ApiOperation(value = "删除一个菜单数据")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseMessage<EmptyData> delete(@PathVariable("id") Long id) {
        return menuService.delete(id);
    }


    @ApiOperation(value = "查询当前登录人菜单数据")
    @RequestMapping(value = "/main/list", method = RequestMethod.GET)
    @HandlerAuth
    public ResponseMessage<List<MenuDTO>> getMenuList() {
        return menuService.getMenuList();
    }


}
