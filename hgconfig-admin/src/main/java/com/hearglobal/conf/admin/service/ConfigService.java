package com.hearglobal.conf.admin.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hearglobal.conf.admin.constant.OperationType;
import com.hearglobal.conf.admin.domain.App;
import com.hearglobal.conf.admin.domain.Config;
import com.hearglobal.conf.admin.domain.ConfigHistory;
import com.hearglobal.conf.admin.domain.Env;
import com.hearglobal.conf.admin.mapper.ConfigMapper;
import com.hearglobal.conf.admin.util.DateUtil;
import com.hearglobal.conf.admin.util.ReturnT;
import com.hearglobal.conf.admin.vo.ConfigParam;
import com.hearglobal.conf.core.ConfigZkClient;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lvzhouyang
 * @Description todo
 * @create 2017-04-03-下午4:51
 */
@Service
public class ConfigService {
    @Resource
    private ConfigMapper configMapper;
    @Resource
    private AppService appService;
    @Resource
    private EnvService envService;
    @Resource
    private ConfigHistoryService configHistoryService;

    public List<Config> getByAppId(int appId) {
        Config config = new Config();
        config.setAppId(Long.parseLong(appId + ""));
        return configMapper.getList(config);
    }


    public Map<String, Object> getListPage(int offset, int pageSize, ConfigParam configParam) {
        PageHelper.offsetPage(offset, pageSize, true);
        List<Config> configList = configMapper.getListParam(configParam);
        PageInfo<Config> pageInfo = new PageInfo<>(configList);

        if (CollectionUtils.isNotEmpty(configList)) {
            for (Config config : configList) {
                config.setRealConfigKey(appService.getById(config.getAppId().intValue()).getName() + "." + config.getConfigKey());

                Env env = envService.getById(config.getEnvId().intValue());
                ConfigZkClient.setEnv(env.getName());

                String realNodeValue = ConfigZkClient.getPathDataByKey(config.getRealConfigKey());
                config.setZkValue(realNodeValue);
            }
        }
        // package result
        Map<String, Object> maps = new HashMap<>();
        maps.put("data", configList);
        maps.put("recordsTotal", pageInfo.getTotal());        // 总记录数
        maps.put("recordsFiltered", pageInfo.getTotal());    // 过滤后的总记录数
        return maps;
    }

    public ReturnT<String> add(Config config) {


        if (config == null) {
            return new ReturnT<>(500, "参数缺失");
        }
        if (config.getAppId() == null) {
            return new ReturnT<>(500, "配置所属APP不能为空");
        }
        App app = appService.getById(config.getAppId().intValue());
        if (app == null) {
            return new ReturnT<>(500, "配置分组不存在");
        }
        if (StringUtils.isBlank(config.getConfigKey())) {
            return new ReturnT<>(500, "配置Key不可为空");
        }
        Config tmp = new Config();
        tmp.setAppId(config.getAppId());
        tmp.setEnvId(config.getEnvId());
        tmp.setConfigKey(config.getConfigKey());
        List<Config> configList = configMapper.getList(tmp);
        if (configList != null && configList.size() > 0) {
            return new ReturnT<>(500, "Key对应的配置已经存在,不可重复添加");
        }
        config.setCreateTime(DateUtil.Date2String(new Date(), DateUtil.DefaultLongFormat));
        config.setUpdateTime(DateUtil.Date2String(new Date(), DateUtil.DefaultLongFormat));
        configMapper.insertSelective(config);

        Env env = envService.getById(config.getEnvId().intValue());
        ConfigZkClient.setEnv(env.getName());

        String groupKey = ConfigZkClient.generateAppConfigKey(app.getName(), config.getConfigKey());
        String path = ConfigZkClient.buildPath(groupKey, env.getName());
        ConfigZkClient.setDataByKey(path, config.getValue());
        ConfigHistory configHistory = configHistoryService.getCommonHistory("", config.getValue(), config, OperationType.ADD);
        configHistoryService.addHistory(configHistory);
        return ReturnT.SUCCESS;
    }

    public ReturnT<String> update(Config config) {
        Config existConfig = configMapper.selectByPrimaryKey(config.getConfigId().intValue());
        if (existConfig == null) {
            return new ReturnT<>(500, "Key对应的配置不存在,请确认");
        }
        int ret = configMapper.updateByPrimaryKeySelective(config);
        if (ret < 1) {
            return new ReturnT<>(500, "Key对应的配置不存在,请确认");
        }
        App app = appService.getById(config.getAppId().intValue());
        Env env = envService.getById(config.getEnvId().intValue());

        ConfigZkClient.setEnv(env.getName());
        if (!StringUtils.equals(existConfig.getValue(), config.getValue())) {
            String groupKey = ConfigZkClient.generateAppConfigKey(app.getName(), config.getConfigKey());
            ConfigZkClient.setPathDataByKey(groupKey, config.getValue());

            ConfigHistory configHistory = configHistoryService.getCommonHistory(existConfig.getValue(), config.getValue(), config, OperationType.UPDATE);
            configHistoryService.addHistory(configHistory);
        }

        return ReturnT.SUCCESS;
    }

    public ReturnT<String> delete(int configId) {
        Config config = configMapper.selectByPrimaryKey(configId);
        if (config == null) {
            return new ReturnT<>(500, "Key对应的配置不存在");
        }
        App app = appService.getById(config.getAppId().intValue());
        Env env = envService.getById(config.getEnvId().intValue());
        ConfigZkClient.setEnv(env.getName());
        configMapper.deleteByPrimaryKey(configId);

        String groupKey = ConfigZkClient.generateAppConfigKey(app.getName(), config.getConfigKey());
        ConfigZkClient.deletePathByKey(groupKey);
        ConfigHistory configHistory = configHistoryService.getCommonHistory(config.getValue(), "", config, OperationType.DELETE);
        configHistoryService.addHistory(configHistory);
        return ReturnT.SUCCESS;
    }
}
