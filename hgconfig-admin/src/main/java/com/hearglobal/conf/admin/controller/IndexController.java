package com.hearglobal.conf.admin.controller;


import com.hearglobal.conf.admin.annotation.PermissionLimit;
import com.hearglobal.conf.admin.controller.interceptor.PermissionInterceptor;
import com.hearglobal.conf.admin.domain.User;
import com.hearglobal.conf.admin.service.LoginService;
import com.hearglobal.conf.admin.service.UserService;
import com.hearglobal.conf.admin.util.ReturnT;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.04.01
 */
@Controller
public class IndexController {
    private static Logger logger = LoggerFactory.getLogger(IndexController.class.getName());

    @Resource
    private LoginService loginService;
    @Resource
    private UserService userService;

    @RequestMapping("/")
    @PermissionLimit(limit = false)
    public String index(Model model, HttpServletRequest request) {
        if (!loginService.ifLogin(request)) {
            return "redirect:/toLogin";
        }
        return "redirect:/config";
    }

    @RequestMapping("/toLogin")
    @PermissionLimit(limit = false)
    public String toLogin(Model model, HttpServletRequest request) {
        if (loginService.ifLogin(request)) {
            return "redirect:/";
        }
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    @PermissionLimit(limit = false)
    public ReturnT<String> loginDo(HttpServletRequest request, HttpServletResponse response, String userName, String password, String ifRemember) {
        if (!loginService.ifLogin(request)) {
            User user = userService.login(userName, password);
            if (user == null) {
                return new ReturnT<>(500, "账号密码错误");
            }
            if (user.getStatus() == 0) {
                return new ReturnT<>(500, "用户已失效");
            }
            boolean ifRem = false;
            if (StringUtils.isNotBlank(ifRemember) && "on".equals(ifRemember)) {
                ifRem = true;
            }
            loginService.login(response, user, ifRem);
        }
        return ReturnT.SUCCESS;
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @ResponseBody
    @PermissionLimit(limit = true)
    public ReturnT<String> logout(HttpServletRequest request, HttpServletResponse response) {
        if (loginService.ifLogin(request)) {
            loginService.logout(request, response);
        }
        return ReturnT.SUCCESS;
    }

}
