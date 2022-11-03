package com.ecut.service;

import com.ecut.model.RedPacketRob;

import java.util.HashMap;

public interface RedPacketRobService {
    /**
     * 抢红包
     *
     * @param redPacketRob
     * @return
     */
    HashMap<String,Object> robRedPacket(RedPacketRob redPacketRob);
}
