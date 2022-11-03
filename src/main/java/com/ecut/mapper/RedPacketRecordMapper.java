package com.ecut.mapper;

import com.ecut.model.RedPacketRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author zhouwei
 */
public interface RedPacketRecordMapper{
    /**
     * 插入数据
     * @param list
     * @return
     */
    int insert(@Param("list") List<RedPacketRecord> list);
}
