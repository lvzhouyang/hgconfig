package com.hearglobal.conf.admin.service;

import com.hearglobal.conf.admin.domain.RoleResource;
import com.hearglobal.conf.admin.mapper.RoleResourceMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * The type Role source service.
 * 角色资源管理
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.04.06
 */
@Service
public class RoleResourceService {

    @Resource
    private RoleResourceMapper roleResourceMapper;

    public List<RoleResource> getByRoleId(Integer roleId) {
        RoleResource roleResource = new RoleResource();
        roleResource.setRoleId(roleId);
        return roleResourceMapper.getList(roleResource);
    }


}
