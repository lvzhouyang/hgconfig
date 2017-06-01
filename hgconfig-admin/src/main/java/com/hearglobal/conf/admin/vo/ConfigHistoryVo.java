package com.hearglobal.conf.admin.vo;


import com.hearglobal.conf.admin.base.BaseEntity;
import com.hearglobal.conf.admin.domain.User;

public class ConfigHistoryVo extends BaseEntity {

    private Long configId;

    private Integer operationType;

    private String operationStr;

    private String oldValue;

    private String newValue;

    private String createTime;

    private Long updateBy;

    private User updateByUser;

    private static final long serialVersionUID = 1L;

    public User getUpdateByUser() {
        return updateByUser;
    }

    public void setUpdateByUser(User updateByUser) {
        this.updateByUser = updateByUser;
    }

    public String getOperationStr() {
        return operationStr;
    }

    public void setOperationStr(String operationStr) {
        this.operationStr = operationStr;
    }

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }
}