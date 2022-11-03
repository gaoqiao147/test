package com.ecut.mapper;

import com.ecut.model.RedPacketRob;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhouwei
 */
public interface RedPacketRobMapper {
    /**
     * 抢红包记录
     *
     * @param redPacketRob
     * @return
     */
    int insert(@Param("redPacketRob") RedPacketRob redPacketRob);
}
