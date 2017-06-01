package com.hearglobal.conf.admin.domain;


import com.hearglobal.conf.admin.base.BaseEntity;

public class User extends BaseEntity {
    private Long userId;

    private String userName;

    private String password;

    private String token;

    private String ownapps;

    private Long roleId;

    private Integer status;

    private static final long serialVersionUID = 1L;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOwnapps() {
        return ownapps;
    }

    public void setOwnapps(String ownapps) {
        this.ownapps = ownapps;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}