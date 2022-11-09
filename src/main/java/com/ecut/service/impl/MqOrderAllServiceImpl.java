package com.ecut.service.impl;

import com.ecut.mapper.MqOrderAllMapper;
import com.ecut.model.MqOrderAll;
import com.ecut.rabbitmq.publisher.DeadPublisher;
import com.ecut.service.MqOrderAllService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * @author zhouwei
 */
@Service
@Slf4j
public class MqOrderAllServiceImpl implements MqOrderAllService {
    @Resource
    MqOrderAllMapper mqOrderAllMapper;

    @Resource
    DeadPublisher deadPublisher;

    @Override
    public Integer saveAllOrder(MqOrderAll mqOrderAll) {
        //0未付款，1已付款
        String id =  UUID.randomUUID().toString();
        mqOrderAll.setState("0")
                .setId(UUID.randomUUID().toString())
                .setOrderId(id)
                .setCreatTime(new Date());
        deadPublisher.send(mqOrderAll.getOrderId());
        int res = mqOrderAllMapper.save(mqOrderAll);
        return res;
    }
}
