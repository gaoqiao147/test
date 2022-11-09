package com.ecut.service.impl;

import com.ecut.mapper.SysLogMapper;
import com.ecut.model.LoginDO;
import com.ecut.model.SysLog;
import com.ecut.service.SysLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * @author zhouwei
 */
@Service
@EnableAsync
public class SysLogServiceImpl implements SysLogService {
    @Resource
    SysLogMapper sysLogMapper;

    @Override
    @Async
    public Integer insert(LoginDO loginDO) {
        SysLog sysLog = new SysLog();
        sysLog.setId(UUID.randomUUID().toString());
        sysLog.setModule("登录模块");
        sysLog.setUserId(loginDO.getUsernumber().toString());
        sysLog.setData(loginDO.toString());
        sysLog.setCreateTime(new Date());
        int res = sysLogMapper.insert(sysLog);
        return res;
    }
}
