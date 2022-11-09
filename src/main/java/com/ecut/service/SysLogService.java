package com.ecut.service;

import com.ecut.model.LoginDO;

/**
 * @author zhouwei
 */
public interface SysLogService {
    /**
     * 插入日志
     *
     * @param loginDO
     * @return
     */
    Integer insert(LoginDO loginDO);
}
