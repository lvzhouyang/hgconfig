package com.hearglobal.conf.admin.constant;

/**
 * The enum Entity status.
 * 实体状态
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.15
 */
public enum EntityStatus {

    DESTROY("废除", 0),

    NORMAL("正常", 1);

    private String name;

    private int index;

    EntityStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (EntityStatus bankStatus : EntityStatus.values()) {
            if (bankStatus.getIndex() == index) {
                return bankStatus.name;
            }
        }
        return null;
    }

    public static Integer getIndex(String name) {
        for (EntityStatus bankStatus : EntityStatus.values()) {
            if (bankStatus.getName().equals(name)) {
                return bankStatus.index;
            }
        }
        return null;
    }

    public static EntityStatus getBankStatus(int index) {
        for (EntityStatus bankStatus : EntityStatus.values()) {
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