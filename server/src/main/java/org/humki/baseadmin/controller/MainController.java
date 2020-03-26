package org.humki.baseadmin.controller;


import org.humki.baseadmin.base.controller.BaseBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用于处理angular路由，防止刷新404
 *
 * @author Kael
 */
@Controller
@RequestMapping("")
public class MainController extends BaseBaseController {

    /**
     * index.html
     */
    @RequestMapping({"", "/"})
    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("routes", "路由跳转");
        request.getRequestDispatcher("index.html").forward(request, response);
    }

    /**
     * 默认处理/main下所有的请求，全部转发到index.html
     */
    @RequestMapping("/main/**")
    public void routes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("routes", "路由跳转");
        // 此处路径要打两点，如果直接写 index.html 会循环访问/web/index.html 造成死循环
        request.getRequestDispatcher("../index.html").forward(request, response);
    }


}