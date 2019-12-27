package org.humki.baseadmin.core.controller;


import org.humki.baseadmin.base.controller.SystemBaseController;
import org.humki.baseadmin.common.annotation.HandlerAuth;
import org.humki.baseadmin.common.pojo.dto.base.message.ResponseMessage;
import org.humki.baseadmin.core.service.AreaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用于处理angular路由，防止刷新404
 *
 * @author Kael
 */
@Controller
@RequestMapping("/area")
public class AreaController extends SystemBaseController {

    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }


}