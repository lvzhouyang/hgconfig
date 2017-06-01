package com.hearglobal.conf.admin.service;

import com.hearglobal.conf.admin.domain.User;
import com.hearglobal.conf.admin.mapper.UserMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * The type User service.
 * 用户
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.04.03
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public User login(String userName, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        List<User> userList = userMapper.getList(user);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        }
        return userList.get(0);
    }

    public User getByToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        User user = new User();
        user.setToken(token);
        List<User> userList = userMapper.getList(user);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        }
        return userList.get(0);
    }

    public User getById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public int update(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
