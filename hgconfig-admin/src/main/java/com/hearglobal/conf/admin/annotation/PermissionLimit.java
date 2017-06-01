package com.hearglobal.conf.admin.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限限制
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.04.01
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionLimit {

	/**
	 * 登陆拦截 (默认拦截)
	 *
	 * @return the boolean
	 * @since 2017.04.01
	 */
	boolean limit() default true;

}