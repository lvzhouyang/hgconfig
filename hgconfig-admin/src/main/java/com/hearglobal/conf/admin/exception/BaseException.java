package com.hearglobal.conf.admin.exception;

/**
 * The type Base exception.
 * 业务异常基类
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.04.06
 */
public class BaseException extends RuntimeException{

    public BaseException(String message) {
        super(message);
    }
}
