package com.hearglobal.conf.admin.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hearglobal.conf.admin.annotation.PermissionLimit;
import com.hearglobal.conf.admin.domain.RoleResource;
import com.hearglobal.conf.admin.domain.User;
import com.hearglobal.conf.admin.exception.AuthException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Authority service.
 * 权限 管理role role-source
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.04.06
 */
@Service
public class AuthorityService {

    private static final String AUTHORITY_LIST_PREFIX = "AUTHORITY_LIST_USER_ID_";

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private UserService userService;
    @Resource
    private RoleResourceService roleResourceService;

    public List<RoleResource> getUserAuth(User user) {
        if (user == null) {
            throw new AuthException("该用户未配置角色,无法登录");
        }
        String resourceStr = redisTemplate.opsForValue().get(AUTHORITY_LIST_PREFIX + user.getUserId());
        if (StringUtils.isNotEmpty(resourceStr)) {
            return JSON.parseObject(resourceStr, new TypeReference<List<RoleResource>>() {
            });
        }
        List<RoleResource> roleResources = roleResourceService.getByRoleId(user.getRoleId().intValue());
        if (CollectionUtils.isEmpty(roleResources)) {
            throw new AuthException("该用户未配置角色,无法登录");
        }
        redisTemplate.opsForValue().set(AUTHORITY_LIST_PREFIX + user.getUserId(), JSON.toJSONString(roleResources));
        return roleResources;
    }

    public void resetAuth(int userId){
        String resourceStr = redisTemplate.opsForValue().get(AUTHORITY_LIST_PREFIX + userId);
        if (StringUtils.isNotEmpty(resourceStr)) {
            redisTemplate.delete(AUTHORITY_LIST_PREFIX + userId);
        }
    }


}
