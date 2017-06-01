package com.hearglobal.conf.admin.mapper;

import com.hearglobal.conf.admin.domain.Config;
import com.hearglobal.conf.admin.base.BaseDao;
import com.hearglobal.conf.admin.vo.ConfigParam;

import java.util.List;

public interface ConfigMapper extends BaseDao<Config> {

    List<Config> getListParam(ConfigParam configParam);
}