package com.hearglobal.conf.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * properties util
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.04.01
 */
public class PropertiesUtil {
	/**
	 * The constant logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

	/**
	 * load prop file
	 *
	 * @param propertyFileName the property file name
	 * @return properties properties
	 * @since 2017.04.01
	 */
	public static Properties loadProperties(String propertyFileName) {
		Properties prop = new Properties();
		InputStream in = null;
		try {
			ClassLoader loder = Thread.currentThread().getContextClassLoader();
			URL url = loder.getResource(propertyFileName); // 方式1：配置更新不需要重启JVM
			if (url != null) {
				in = new FileInputStream(url.getPath());
				// in = loder.getResourceAsStream(propertyFileName); // 方式2：配置更新需重启JVM
				prop.load(in);
			}
		} catch (IOException e) {
			logger.error("load {} error!", propertyFileName);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("close {} error!", propertyFileName);
				}
			}
		}
		return prop;
	}

	/**
	 * Load file properties properties.
	 *
	 * @param propertyFileName the property file name
	 * @return the properties
	 * @since 2017.04.01
	 */
	public static Properties loadFileProperties(String propertyFileName) {
		Properties prop = new Properties();
		InputStream in = null;
		try {
			ClassLoader loder = Thread.currentThread().getContextClassLoader();
			URL url = url = new File(propertyFileName).toURI().toURL();
			in = new FileInputStream(url.getPath());
			prop.load(in);
		} catch (IOException e) {
			logger.error("load {} error!", propertyFileName);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("close {} error!", propertyFileName);
				}
			}
		}
		return prop;
	}

	/**
	 * load prop value of string
	 *
	 * @param prop the prop
	 * @param key  the key
	 * @return string
	 */
	public static String getString(Properties prop, String key) {
		return prop.getProperty(key);
	}

	/**
	 * load prop value of int
	 *
	 * @param prop the prop
	 * @param key  the key
	 * @return int
	 */
	public static int getInt(Properties prop, String key) {
		return Integer.parseInt(getString(prop, key));
	}

	/**
	 * load prop value of boolean
	 *
	 * @param prop the prop
	 * @param key  the key
	 * @return boolean
	 */
	public static boolean getBoolean(Properties prop, String key) {
		return Boolean.valueOf(getString(prop, key));
	}

}
