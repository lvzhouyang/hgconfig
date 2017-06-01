package com.hearglobal.conf.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 环境基类
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.04.01
 */
public class Environment {

    /**
     * conf data path in zk
     */
    public static final String CONF_DATA_PATH = "/hgconfig";

    /**
     * zk config file
     */
    private static final String ZK_ADDRESS_FILE = "/zk-conf.properties";

    /**
     * zk address
     */
    public static final String ZK_ADDRESS;        // zk地址：格式	ip1:port,ip2:port,ip3:port

    static {
        InputStream inputStream = Environment.class.getResourceAsStream(ZK_ADDRESS_FILE);
        Properties prop = new Properties();
        try {
            prop.load(inputStream);
        } catch (IOException ignored) {

        }
        ZK_ADDRESS = PropertiesUtil.getString(prop, "zkserver");
    }
}

