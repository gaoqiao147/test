package com.ecut.rabbitmq.consumer;

import com.ecut.service.MqOrderFailService;
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
public class DeadConsumer {
    @Resource
    MqOrderFailService mqOrderFailService;

    @RabbitListener(queues = "${mq.customer.queue.name}",containerFactory = "singleListenerContainer")
    public void consumerOrder(@Payload String orderId){
        try {
            log.info("成功接收数据{}",orderId);
            mqOrderFailService.saveFailOrder(orderId);
        }catch (Exception e){
            log.error("数据处理失败！{}",e.getMessage());
        }
    }
}
