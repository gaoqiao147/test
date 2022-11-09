package com.ecut.rabbitmq.publisher;

import com.ecut.model.LoginDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author zhouwei
 */
@Component
@Slf4j
public class DeadPublisher {
    @Resource
    RabbitTemplate rabbitTemplate;
    @Resource
    Environment env;

    public void send(String orderId) {
        try {
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(env.getProperty("mq.producer.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("mq.producer.routing.key.name"));
            rabbitTemplate.convertAndSend(orderId, message -> {
                MessageProperties messageProperties = message.getMessageProperties();
                messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, String.class);
                log.info("消息发送成功到死信队列{}", message);
                return message;
            });
        } catch (Exception e) {
            log.error("发送数据失败！{}",e.getMessage());
        }
    }
}
