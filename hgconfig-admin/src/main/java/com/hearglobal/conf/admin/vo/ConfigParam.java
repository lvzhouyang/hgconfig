package com.hearglobal.conf.admin.vo;

import java.io.Serializable;

/**
 * The type Config param.
 * 配置查询
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.04.03
 */
public class ConfigParam implements Serializable{

    private Integer envId;
    private Integer appId;
    private String configKey;

    public Integer getEnvId() {
        return envId;
    }

    public void setEnvId(Integer envId) {
        this.envId = envId;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }
}
