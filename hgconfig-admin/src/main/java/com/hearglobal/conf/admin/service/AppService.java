package com.hearglobal.conf.admin.service;

import com.hearglobal.conf.admin.domain.App;
import com.hearglobal.conf.admin.mapper.AppMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * The type App service.
 * APP管理
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.04.03
 */
@Service
public class AppService {

    @Resource
    private AppMapper appMapper;

    public int addApp(App app) {
        return appMapper.insertSelective(app);
    }

    public int updateApp(App app) {
        return appMapper.updateByPrimaryKeySelective(app);
    }

    public List<App> findAll() {
        return appMapper.getList(new App());
    }

    public App getById(int id){
        return appMapper.selectByPrimaryKey(id);
    }

    public int delete(int id){
        return appMapper.deleteByPrimaryKey(id);
    }
}
