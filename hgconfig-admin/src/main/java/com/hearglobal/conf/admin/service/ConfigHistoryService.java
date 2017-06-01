package com.hearglobal.conf.admin.service;

import com.hearglobal.conf.admin.constant.OperationType;
import com.hearglobal.conf.admin.domain.Config;
import com.hearglobal.conf.admin.domain.ConfigHistory;
import com.hearglobal.conf.admin.mapper.ConfigHistoryMapper;
import com.hearglobal.conf.admin.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * The type Config history service.
 * Description
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.04.05
 */
@Service
public class ConfigHistoryService {

    @Resource
    private ConfigHistoryMapper configHistoryMapper;


    public int addHistory(ConfigHistory configHistory) {
        return configHistoryMapper.insertSelective(configHistory);
    }

    public ConfigHistory getCommonHistory(String oldValue, String newValue, Config config, OperationType operationType) {
        ConfigHistory configHistory = new ConfigHistory();
        configHistory.setOldValue(oldValue);
        configHistory.setNewValue(newValue);
        configHistory.setConfigId(config.getConfigId());
        configHistory.setCreateTime(DateUtil.Date2String(new Date(), DateUtil.DefaultLongFormat));
        configHistory.setUpdateBy(AppThreadCache.getUser().getUserId());
        configHistory.setOperationType(operationType.getIndex());
        return configHistory;
    }

    public List<ConfigHistory> getByConfigId(Long configId){
        ConfigHistory configHistory = new ConfigHistory();
        configHistory.setConfigId(configId);
        return configHistoryMapper.getList(configHistory);
    }
}
