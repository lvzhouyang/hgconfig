package com.hearglobal.conf.admin.controller;


import com.hearglobal.conf.admin.annotation.PermissionLimit;
import com.hearglobal.conf.admin.domain.User;
import com.hearglobal.conf.admin.service.AuthorityService;
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
@RequestMapping("/probe")
public class ProbeController {
    private static Logger logger = LoggerFactory.getLogger(ProbeController.class.getName());

    @Resource
    private AuthorityService authorityService;

    @RequestMapping(value = "resetAuth", method = RequestMethod.GET)
    @ResponseBody
    @PermissionLimit(limit = false)
    public ReturnT<String> loginDo(int userId) {
        authorityService.resetAuth(userId);
        return ReturnT.SUCCESS;
    }


}
