package com.hearglobal.conf.core;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Arrays;
import java.util.List;

/**
 * The type Config util adapter.
 * 包装获取配置
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.04.01
 */
public class ConfigUtilAdapter {

    public static String getString(String key) {
        return ConfigClient.get(key, "");
    }

    public static Integer getInteger(String key) {
        return NumberUtils.toInt(ConfigClient.get(key, ""));
    }

    public static Boolean getBoolean(String key) {
        return BooleanUtils.toBoolean(ConfigClient.get(key, ""));
    }

    public static Long getLong(String key) {
        return NumberUtils.toLong(getString(key));
    }

    public static Float getFloat(String key) {
        return NumberUtils.toFloat(getString(key));
    }

    public static Double getDouble(String key) {
        return NumberUtils.toDouble(getString(key));
    }

    public static List<String> getStrList(String key, String splitChar) {
        String value = getString(key);
        return Arrays.asList(value.split(splitChar));
    }

    public static List<String> getStrList(String key) {
        return getStrList(key, ",");
    }


    public static List<String> getAllKey() {
        return ConfigZkClient.getAllKey();
    }
}
