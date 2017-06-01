package com.hearglobal.conf.admin.domain;


import com.hearglobal.conf.admin.base.BaseEntity;

public class Config extends BaseEntity {
    private Long configId;

    private String configKey;

    private String value;

    private Long appId;

    private Long envId;

    private String createTime;

    private String updateTime;

    private String realConfigKey;

    private String zkValue;

    private static final long serialVersionUID = 1L;

    public String getZkValue() {
        return zkValue;
    }

    public void setZkValue(String zkValue) {
        this.zkValue = zkValue;
    }

    public String getRealConfigKey() {
        return realConfigKey;
    }

    public void setRealConfigKey(String realConfigKey) {
        this.realConfigKey = realConfigKey;
    }

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getEnvId() {
        return envId;
    }

    public void setEnvId(Long envId) {
        this.envId = envId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}