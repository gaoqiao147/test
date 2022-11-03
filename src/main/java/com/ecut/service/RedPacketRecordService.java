package com.ecut.service;

import com.ecut.model.RedPacketRecord;
import com.ecut.model.RedPacketSend;

import java.util.List;

/**
 * @author zhouwei
 */
public interface RedPacketRecordService {
    /**
     * 插入数据库操作
     *
     * @param redPacketSend
     * @param list
     * @return
     */
    int insert(RedPacketSend redPacketSend, List<RedPacketRecord> list);
}
