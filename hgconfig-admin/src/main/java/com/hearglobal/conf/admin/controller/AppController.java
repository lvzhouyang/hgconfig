package com.hearglobal.conf.admin.controller;

import com.hearglobal.conf.admin.annotation.PermissionLimit;
import com.hearglobal.conf.admin.domain.App;
import com.hearglobal.conf.admin.domain.Config;
import com.hearglobal.conf.admin.service.AppService;
import com.hearglobal.conf.admin.service.ConfigService;
import com.hearglobal.conf.admin.util.DateUtil;
import com.hearglobal.conf.admin.util.ReturnT;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * The type App controller.
 * Description
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.04.03
 */
@Controller
@RequestMapping("/app")
public class AppController {
    private static Logger logger = LoggerFactory.getLogger(AppController.class);

    @Resource
    private AppService appService;
    @Resource
    private ConfigService configService;


    @RequestMapping
    @PermissionLimit
    public String index(Model model) {

        List<App> list = appService.findAll();
        model.addAttribute("list", list);
        return "app/app";
    }


    @RequestMapping(value = "/save")
    @ResponseBody
    @PermissionLimit
    public ReturnT<String> save(App app) {

        // valid
        if (app.getName() == null || StringUtils.isBlank(app.getName())) {
            return new ReturnT<>(500, "请输入应用名");
        }
        if (app.getName().length() < 4 || app.getName().length() > 100) {
            return new ReturnT<>(500, "应用名长度限制为4~100");
        }

        // valid repeat
        app.setCreateTime(DateUtil.Date2String(new Date(), DateUtil.DefaultLongFormat));
        app.setUpdateTime(DateUtil.Date2String(new Date(), DateUtil.DefaultLongFormat));
        int ret = appService.addApp(app);
        return (ret > 0) ? ReturnT.SUCCESS : ReturnT.FAIL;
    }

    @RequestMapping("/update")
    @ResponseBody
    @PermissionLimit
    public ReturnT<String> update(App app) {

        // valid
        if (app.getName() == null || StringUtils.isBlank(app.getName())) {
            return new ReturnT<>(500, "请输入应用名");
        }
        if (app.getName().length() < 4 || app.getName().length() > 100) {
            return new ReturnT<>(500, "应用名长度限制为4~100");
        }
        app.setUpdateTime(DateUtil.Date2String(new Date(), DateUtil.DefaultLongFormat));
        int ret = appService.updateApp(app);
        return (ret > 0) ? ReturnT.SUCCESS : ReturnT.FAIL;
    }


    @RequestMapping("/remove")
    @ResponseBody
    @PermissionLimit
    public ReturnT<String> remove(int appId) {

        // valid
        List<Config> configList = configService.getByAppId(appId);
        if (CollectionUtils.isNotEmpty(configList)) {
            return new ReturnT<>(500, "删除失败, 该分组存在使用中的属性");
        }

        int ret = appService.delete(appId);
        return (ret > 0) ? ReturnT.SUCCESS : ReturnT.FAIL;
    }
}
