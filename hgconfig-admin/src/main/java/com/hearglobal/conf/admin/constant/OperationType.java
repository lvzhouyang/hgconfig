package com.hearglobal.conf.admin.constant;

/**
 * The enum Entity status.
 * 实体状态
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.15
 */
public enum OperationType {

    ADD("新增", 1),
    DELETE("删除", 3),
    UPDATE("修改", 2);

    private String name;

    private int index;

    OperationType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (OperationType bankStatus : OperationType.values()) {
            if (bankStatus.getIndex() == index) {
                return bankStatus.name;
            }
        }
        return null;
    }

    public static Integer getIndex(String name) {
        for (OperationType bankStatus : OperationType.values()) {
            if (bankStatus.getName().equals(name)) {
                return bankStatus.index;
            }
        }
        return null;
    }

    public static OperationType getBankStatus(int index) {
        for (OperationType bankStatus : OperationType.values()) {
            if (bankStatus.getIndex() == index) {
                return bankStatus;
            }
        }
        return null;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

}