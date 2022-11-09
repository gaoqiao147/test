package com.ecut.service.impl;

import com.ecut.mapper.MqOrderAllMapper;
import com.ecut.mapper.MqOrderFailMapper;
import com.ecut.model.MqOrderAll;
import com.ecut.model.MqOrderFail;
import com.ecut.service.MqOrderFailService;
import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * @author zhouwei
 */
@Service
@Slf4j
public class MqOrderFailServiceImpl implements MqOrderFailService {
    @Resource
    MqOrderAllMapper mqOrderAllMapper;

    @Resource
    MqOrderFailMapper mqOrderFailMapper;

    @Override
    public Integer saveFailOrder(String orderId) {
        MqOrderAll mqOrderAll = mqOrderAllMapper.findOrder(orderId);
        if(mqOrderAll != null && Objects.equals(mqOrderAll.getState(), "0")){
            MqOrderFail mqOrderFail = new MqOrderFail();
            mqOrderFail.setId(UUID.randomUUID().toString())
                    .setOrderId(mqOrderAll.getOrderId())
                    .setOrderName(mqOrderAll.getOrderName())
                    .setCreatTime(new Date());
            return mqOrderFailMapper.save(mqOrderFail);
        }
        return 0;
    }
}
