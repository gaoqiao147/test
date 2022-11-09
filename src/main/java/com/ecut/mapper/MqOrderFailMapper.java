package com.ecut.mapper;

import com.ecut.model.MqOrderAll;
import com.ecut.model.MqOrderFail;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhouwei
 */
public interface MqOrderFailMapper {
    /**
     * @param mqOrderFail
     * @return
     */
    int save(@Param("mqOrderFail") MqOrderFail mqOrderFail);
}
