package com.hearglobal.conf.admin.service;

import com.hearglobal.conf.admin.domain.User;
import com.hearglobal.conf.admin.util.CookieUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * The type Login service.
 * Description
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.04.03
 */
@Service
public class LoginService {

    @Resource
    private UserService userService;

    private static final String LOGIN_IDENTITY_KEY = "LOGIN_IDENTITY";

    public boolean login(HttpServletResponse response, User user, boolean ifRemember) {
        String token = UUID.randomUUID().toString().replace("-", "");
        CookieUtil.set(response, LOGIN_IDENTITY_KEY, token, ifRemember);
        user.setToken(token);
        userService.update(user);
        return true;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String identityInfo = CookieUtil.getValue(request, LOGIN_IDENTITY_KEY);
        CookieUtil.remove(request, response, LOGIN_IDENTITY_KEY);
        User user = userService.getByToken(identityInfo);
        if (user != null) {
            user.setToken("");
            userService.update(user);
        }
    }

    public boolean ifLogin(HttpServletRequest request) {
        String identityInfo = CookieUtil.getValue(request, LOGIN_IDENTITY_KEY);
        if (StringUtils.isEmpty(identityInfo) || userService.getByToken(identityInfo) == null) {
            return false;
        }
        return true;
    }

    public User getLoginUser(HttpServletRequest request){
        String identityInfo = CookieUtil.getValue(request, LOGIN_IDENTITY_KEY);
        return userService.getByToken(identityInfo);
    }
}
