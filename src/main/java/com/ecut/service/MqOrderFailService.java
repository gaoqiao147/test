package com.ecut.service;

/**
 * @author zhouwei
 */
public interface MqOrderFailService {
    /**
     * 更新失败订单
     * @param orderId
     * @return
     */
    Integer saveFailOrder(String orderId);
}
