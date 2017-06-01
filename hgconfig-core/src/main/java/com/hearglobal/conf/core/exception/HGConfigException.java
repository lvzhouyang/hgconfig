package com.hearglobal.conf.core.exception;

/**
 * The type Hg config exception.
 * 定义配置系统异常
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.04.03
 */
public class HGConfigException extends RuntimeException {
    public HGConfigException(String message) {
        super(message);
    }
}
