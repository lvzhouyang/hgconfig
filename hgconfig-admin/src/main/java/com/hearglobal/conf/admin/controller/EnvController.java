package com.hearglobal.conf.admin.controller;

import com.hearglobal.conf.admin.annotation.PermissionLimit;
import com.hearglobal.conf.admin.domain.Env;
import com.hearglobal.conf.admin.service.EnvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lvzhouyang
 * @Description todo
 * @create 2017-04-03-下午4:46
 */
@Controller
@RequestMapping("/env")
public class EnvController {
    private static Logger logger = LoggerFactory.getLogger(EnvController.class);

    @Resource
    private EnvService envService;


    @RequestMapping
    @PermissionLimit(limit = true)
    public String index(Model model) {

        List<Env> list = envService.findAll();
        model.addAttribute("list", list);
        return "env/env";
    }
}
