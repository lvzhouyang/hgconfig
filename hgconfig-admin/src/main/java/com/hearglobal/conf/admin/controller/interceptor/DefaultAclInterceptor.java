package com.hearglobal.conf.admin.controller.interceptor;

import com.hearglobal.conf.admin.domain.User;
import com.hearglobal.conf.admin.service.AppThreadCache;
import com.hearglobal.conf.admin.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 鉴权拦截器
 * 请求时间记录
 * Created by lvzhouyang on 16/12/14.
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.27
 */
@Component
public class DefaultAclInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(DefaultAclInterceptor.class);

    @Resource
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        AppThreadCache.setStartTime();
        User user = loginService.getLoginUser(httpServletRequest);
        if (user != null){
            AppThreadCache.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        String uri = this.getUri(httpServletRequest);
        if (!uri.endsWith(".ajax") && (uri.indexOf('.') != -1 || uri.contains("/cron/"))) {
            return;
        }
        long time = AppThreadCache.getStartTime();
        if (time > 0) {
            long timeCost = System.currentTimeMillis() - time;
            logger.info("time used, {}, {}ms", httpServletRequest.getRequestURI(), timeCost);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private String getUri(HttpServletRequest request) {
        String uri = request.getRequestURI().replaceAll(";.*", "");
        return uri.substring(request.getContextPath().length());
    }
}
