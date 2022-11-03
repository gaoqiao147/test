package com.ecut.service.impl;

import com.ecut.mapper.RedPacketRecordMapper;
import com.ecut.mapper.RedPacketSendMapper;
import com.ecut.model.RedPacketRecord;
import com.ecut.model.RedPacketSend;
import com.ecut.service.RedPacketRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhowei
 * @date 2022/11/3
 */
@Service
@Slf4j
public class RedPacketRecordServiceImpl implements RedPacketRecordService {
    @Resource
    RedPacketRecordMapper redPacketRecordMapper;
    @Resource
    RedPacketSendMapper redPacketSendMapper;

    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public int insert(RedPacketSend redPacketSend, List<RedPacketRecord> list) {
        int send = redPacketSendMapper.insert(redPacketSend);
        int record = redPacketRecordMapper.insert(list);
        return send + record;
    }
}
