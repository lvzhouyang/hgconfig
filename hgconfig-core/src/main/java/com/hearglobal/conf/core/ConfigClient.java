package com.hearglobal.conf.core;

import com.hearglobal.conf.core.util.Environment;
import com.hearglobal.conf.core.util.PropertiesUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

/**
 * local cache
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.04.01
 */
public class ConfigClient {

    /**
     * The constant logger.
     */
    private static Logger logger = LoggerFactory.getLogger(ConfigClient.class);
    /**
     * The constant localProp.
     */
    public static Properties localProp = PropertiesUtil.loadProperties("local.properties");
    /**
     * The constant cache.
     */
    private static Cache cache;

    static {
        CacheManager manager = CacheManager.create();    // default use ehcche.xml under src
        cache = new Cache(Environment.CONF_DATA_PATH, 10000, false, true, 1800, 1800);
        manager.addCache(cache);
    }

    /**
     * Set.
     *
     * @param key   the key
     * @param value the value
     * @since 2017.04.01
     */
    public static void set(String key, String value) {
        if (cache != null) {
            logger.info("ConfigClient hgconfig: 初始化配置: [{}:{}]", new Object[]{key, value});
            cache.put(new Element(key, value));
        }
    }

    /**
     * Update.
     *
     * @param key   the key
     * @param value the value
     * @since 2017.04.01
     */
    public static void update(String key, String value) {
        if (cache != null) {
            if (cache.get(key) != null) {
                logger.info("ConfigClient hgconfig: 更新配置: [{}:{}]", new Object[]{key, value});
                cache.put(new Element(key, value));
            }
        }
    }

    /**
     * Get string.
     *
     * @param key        the key
     * @param defaultVal the default val
     * @return the string
     * @since 2017.04.01
     */
    public static String get(String key, String defaultVal) {
        if (localProp != null && localProp.containsKey(key)) {
            return localProp.getProperty(key);
        }
        if (cache != null) {
            Element element = cache.get(key);
            if (element != null) {
                return (String) element.getObjectValue();
            }
        }
        String zkData = ConfigZkClient.getPathDataByKey(key);
        if (zkData != null) {
            set(key, zkData);
            return zkData;
        }

        return defaultVal;
    }

    /**
     * Remove boolean.
     *
     * @param key the key
     * @return the boolean
     * @since 2017.04.01
     */
    public static boolean remove(String key) {
        if (cache != null) {
            logger.info("ConfigClient hgconfig: 删除配置：key ", key);
            return cache.remove(key);
        }
        return false;
    }
}
