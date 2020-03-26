package org.humki.baseadmin.core.controller;


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
public class AreaController extends CoreBaseController {

    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }


}