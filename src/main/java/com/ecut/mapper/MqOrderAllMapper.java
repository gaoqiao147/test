package com.ecut.mapper;

import com.ecut.model.MqOrderAll;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhouwei
 */
public interface MqOrderAllMapper {
    /**
     * 添加记录
     * @param mqOrderAll
     * @return
     */
    int save(@Param("mqOrderAll") MqOrderAll mqOrderAll);

    /**
     * 查询记录
     *
     * @param orderId
     * @return
     */
    MqOrderAll findOrder(@Param("orderId") String orderId);
}
