package com.hearglobal.conf.admin.controller.interceptor;

import com.alibaba.fastjson.JSON;
import com.hearglobal.conf.admin.annotation.PermissionLimit;
import com.hearglobal.conf.admin.domain.RoleResource;
import com.hearglobal.conf.admin.domain.User;
import com.hearglobal.conf.admin.exception.AuthException;
import com.hearglobal.conf.admin.service.AuthorityService;
import com.hearglobal.conf.admin.service.LoginService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限拦截, 简易版
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.04.01
 */
@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);
    @Resource
    private LoginService loginService;
    @Resource
    private AuthorityService authorityService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return super.preHandle(request, response, handler);
        }
        HandlerMethod method = (HandlerMethod) handler;
        PermissionLimit permission = method.getMethodAnnotation(PermissionLimit.class);
        if (permission == null || !permission.limit()) {
            return super.preHandle(request, response, handler);
        }
        User user = loginService.getLoginUser(request);

        if (user == null) {
            throw new AuthException("登录失败!");
        }


        List<RoleResource> roleResourceList = authorityService.getUserAuth(user);
        if (CollectionUtils.isEmpty(roleResourceList)) {
            logger.error("no auth roleResourceList:{}", JSON.toJSON(roleResourceList));
            throw new AuthException("登录失败!");
        }
        List<String> urlList = getUrl(roleResourceList);
        if (!urlList.contains(getUri(request))) {
            logger.error("no auth target url:{},authList:{}", getUri(request), JSON.toJSON(urlList));
            throw new AuthException("没有该功能访问权限!");
        }
        request.setAttribute("roleResourceList", roleResourceList);
        request.setAttribute("userInfo", user);
        return super.preHandle(request, response, handler);
    }

    private String getUri(HttpServletRequest request) {
        String uri = request.getRequestURI().replaceAll(";.*", "");
        return uri.substring(request.getContextPath().length());
    }

    public List<String> getUrl(List<RoleResource> roleResources) {
        List<String> url = new ArrayList<>();
        for (RoleResource roleResource : roleResources) {
            url.add(roleResource.getUrlPattern());
        }
        return url;
    }

}
