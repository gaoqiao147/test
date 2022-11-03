package com.ecut.mapper;

import com.ecut.model.RedPacketSend;
import org.apache.ibatis.annotations.Param;

/**
 * 发红包
 * @author zhouwei
 */
public interface RedPacketSendMapper {
    /**
     * 插入红包记录
     * @param redPacketSend
     * @return
     */
    int insert(@Param("redPacketSend") RedPacketSend redPacketSend);
}
