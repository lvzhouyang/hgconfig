package com.hearglobal.conf.admin.controller;

import com.hearglobal.conf.admin.annotation.PermissionLimit;
import com.hearglobal.conf.admin.constant.OperationType;
import com.hearglobal.conf.admin.domain.App;
import com.hearglobal.conf.admin.domain.Config;
import com.hearglobal.conf.admin.domain.ConfigHistory;
import com.hearglobal.conf.admin.domain.Env;
import com.hearglobal.conf.admin.service.*;
import com.hearglobal.conf.admin.util.Assembler;
import com.hearglobal.conf.admin.util.ReturnT;
import com.hearglobal.conf.admin.vo.ConfigHistoryVo;
import com.hearglobal.conf.admin.vo.ConfigParam;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The type Config controller.
 * Description
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.04.03
 */
@Controller
@RequestMapping("/config")
public class ConfigController {

    @Resource
    private ConfigService configService;
    @Resource
    private AppService appService;
    @Resource
    private EnvService envService;
    @Resource
    private ConfigHistoryService configHistoryService;
    @Resource
    private UserService userService;

    @RequestMapping("")
    @PermissionLimit
    public String index(Model model, String znodeKey) {

        List<App> apps = appService.findAll();
        List<Env> envs = envService.findAll();
        model.addAttribute("appList", apps);
        model.addAttribute("envList", envs);

        return "config/config";
    }

    @RequestMapping("/pageList")
    @ResponseBody
    @PermissionLimit
    public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int start,
                                        @RequestParam(required = false, defaultValue = "10") int length,
                                        ConfigParam configParam) {


        return configService.getListPage(start, length,configParam);
    }

    @RequestMapping("/add")
    @ResponseBody
    @PermissionLimit
    public ReturnT<String> add(Config config) {
        return configService.add(config);
    }

    @RequestMapping("/update")
    @ResponseBody
    @PermissionLimit
    public ReturnT<String> update(Config config) {
        return configService.update(config);
    }

    @RequestMapping("/remove")
    @ResponseBody
    @PermissionLimit
    public ReturnT<String> remove(int configId) {
        return configService.delete(configId);
    }

    @RequestMapping("/history")
    @PermissionLimit
    public String index(Model model,long configId) {

        List<ConfigHistory> configHistories = configHistoryService.getByConfigId(configId);
        List<ConfigHistoryVo> configHistoryVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(configHistories)){
            for (ConfigHistory configHistory : configHistories){
                ConfigHistoryVo configHistoryVo = new ConfigHistoryVo();
                Assembler.assemble(configHistory,configHistoryVo);
                configHistoryVo.setOperationStr(OperationType.getName(configHistory.getOperationType()));
                configHistoryVo.setUpdateByUser(userService.getById(configHistory.getUpdateBy().intValue()));
                configHistoryVos.add(configHistoryVo);
            }
        }
        Collections.reverse(configHistoryVos);
        model.addAttribute("list", configHistoryVos);
        return "config/config_history";
    }

}
