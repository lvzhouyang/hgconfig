package com.hearglobal.conf.admin.domain;

import com.hearglobal.conf.admin.base.BaseEntity;

public class Env extends BaseEntity {
    private Long envId;

    private String name;

    private static final long serialVersionUID = 1L;

    public Long getEnvId() {
        return envId;
    }

    public void setEnvId(Long envId) {
        this.envId = envId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}