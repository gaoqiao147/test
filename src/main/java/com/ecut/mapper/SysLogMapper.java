package com.ecut.mapper;

import com.ecut.model.SysLog;

/**
 * @author zhouwei
 */
public interface SysLogMapper {
    /**
     * 记录日志
     *
     * @param record
     * @return
     */
    int insert(SysLog record);
}
