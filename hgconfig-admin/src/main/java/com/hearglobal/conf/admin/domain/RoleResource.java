package com.hearglobal.conf.admin.domain;


import com.hearglobal.conf.admin.base.BaseEntity;

public class RoleResource extends BaseEntity {
    private Integer roleResId;

    private Integer roleId;

    private String urlPattern;

    private String urlDescription;

    private String methodMask;

    private String updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getRoleResId() {
        return roleResId;
    }

    public void setRoleResId(Integer roleResId) {
        this.roleResId = roleResId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public String getUrlDescription() {
        return urlDescription;
    }

    public void setUrlDescription(String urlDescription) {
        this.urlDescription = urlDescription;
    }

    public String getMethodMask() {
        return methodMask;
    }

    public void setMethodMask(String methodMask) {
        this.methodMask = methodMask;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}