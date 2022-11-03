package com.ecut.service;

import com.ecut.model.RedPacketSend;

import java.util.HashMap;

/**
 * @author zhouwei
 */
public interface RedPacketSendService {
    /**
     * 发红包
     *
     * @param redPacketSend
     * @return
     */
    HashMap<String,Object> redPacketSend(RedPacketSend redPacketSend);
}
