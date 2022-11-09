package com.ecut.rabbitmq.consumer;

import com.ecut.model.LoginDO;
import com.ecut.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhouwei
 */
@Component
@Slf4j
public class LogConsumer {
    @Resource
    SysLogService sysLogService;

    @RabbitListener(queues = "${mq.basic.info.queue.name}", containerFactory = "singleListenerContainer")
    public void receiveMessage(@Payload LoginDO loginDO) {
        try {
            log.info("接收数据{}", loginDO);
            sysLogService.insert(loginDO);
        } catch (Exception e) {
            log.error("发生异常", e.fillInStackTrace());
        }
    }
}
