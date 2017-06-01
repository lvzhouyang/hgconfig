package com.hearglobal.conf.admin.service;

import com.hearglobal.conf.admin.domain.Env;
import com.hearglobal.conf.admin.mapper.EnvMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lvzhouyang
 * @Description todo
 * @create 2017-04-03-下午4:44
 */
@Service
public class EnvService {

    @Resource
    private EnvMapper envMapper;


    public List<Env> findAll() {
        return envMapper.getList(new Env());
    }

    public Env getById(int id){
        return envMapper.selectByPrimaryKey(id);
    }

}
