package com.hearglobal.conf.admin.util;


import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Jackson util
 * <p>
 * 1、obj need private and set/get；
 * 2、do not support inner class；
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.04.01
 */
public class JacksonUtil {
    /**
     * The constant logger.
     */
    private static Logger logger = LoggerFactory.getLogger(JacksonUtil.class);

    /**
     * The constant objectMapper.
     */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    /**
     * bean、array、List、Map --> json
     *
     * @param obj the obj
     * @return string string
     * @throws Exception
     * @since 2017.04.01
     */
    public static String writeValueAsString(Object obj) {
        try {
            return getInstance().writeValueAsString(obj);
        } catch (JsonGenerationException e) {
            logger.error("", e);
        } catch (JsonMappingException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * string --> bean、Map、List(array)
     *
     * @param <T>     the type parameter
     * @param jsonStr the json str
     * @param clazz   the clazz
     * @return t t
     * @throws Exception
     * @since 2017.04.01
     */
    public static <T> T readValue(String jsonStr, Class<T> clazz) {
        try {
            return getInstance().readValue(jsonStr, clazz);
        } catch (JsonParseException e) {
            logger.error("", e);
        } catch (JsonMappingException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * Read value refer t.
     *
     * @param <T>     the type parameter
     * @param jsonStr the json str
     * @param clazz   the clazz
     * @return the t
     * @since 2017.04.01
     */
    public static <T> T readValueRefer(String jsonStr, Class<T> clazz) {
        try {
            return getInstance().readValue(jsonStr, new TypeReference<T>() {
            });
        } catch (JsonParseException e) {
            logger.error("", e);
        } catch (JsonMappingException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        return null;
    }
}
