package com.hearglobal.conf.example.controller;

import com.hearglobal.conf.core.ConfigUtilAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class IndexController {
    @Resource
    private Configuration configuration;

    /**
     * 说明: API方式获取, 只需要执行diamante "ConfigClient.get("key", null)" 即可,
     * 在业务中使用比较方便 ,而且接受HGCONFIG实时推送更新。 同时因为底层有配置缓存,并不存在性能问题;
     */
    @RequestMapping("")
    @ResponseBody
    public String index() {

        String paramByClient = ConfigUtilAdapter.getString("hkcontent.test01");
        String result = "XML:<hr>hkcontent.test=" + ConfigUtilAdapter.getString("hkcontent.test");
        result += "<br><br><br>API:<hr>dhkcontent.test01=" + paramByClient;
        result += "<br><br><br>API:<hr>hkcontent.switch=" + ConfigUtilAdapter.getBoolean("hkcontent.switch");
        result += "configuration=" + configuration.paramByXml;
        return result;
    }
}
