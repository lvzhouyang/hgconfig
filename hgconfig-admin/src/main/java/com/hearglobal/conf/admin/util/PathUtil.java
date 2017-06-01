package com.hearglobal.conf.admin.util;


/**
 * 资源路径Util
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.04.01
 */
public class PathUtil {

    /**
     * ClassPath (通常properties 目录)
     *
     * @return string
     * @since 2017.04.01
     */
    public static String classPath() {
        return Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }

    /**
     * Web跟路径 (通常为样式文件/静态页面 目录) (也可以根据request获取:request.getRealPath("/") )
     *
     * @return string
     * @since 2017.04.01
     */
    public static String webPath() {
        String realPath = classPath();
        int wei = realPath.lastIndexOf("WEB-INF/classes/");
        if (wei > -1) {
            realPath = realPath.substring(0, wei);
        }
        return realPath;
    }

    /**
     * WEB-INF目录 (通常为template文件 目录)
     *
     * @return string
     * @since 2017.04.01
     */
    public static String webInfPath() {
        String realPath = classPath();
        int wei = realPath.lastIndexOf("WEB-INF/classes/");
        if (wei > -1) {
            realPath = realPath.substring(0, wei);
        }
        return realPath + "WEB-INF/";
    }

}
