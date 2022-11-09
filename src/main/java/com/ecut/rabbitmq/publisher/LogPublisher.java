package com.ecut.rabbitmq.publisher;

import com.ecut.model.LoginDO;
import lombok.extern.slf4j.Slf4j;
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
public class LogPublisher {
    /**
     * 定义操作组件
     */
    @Resource
    RabbitTemplate rabbitTemplate;
    /**
     * 定义环境变量读取实例env
     */
    @Resource
    Environment env;

    /**
     * 将用户定义成功的信息发送到队列
     */
    public void sendLogMsg(LoginDO loginDO) {
        try {
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(env.getProperty("mq.basic.info.exchange.name"));
            rabbitTemplate.setRoutingKey(Objects.requireNonNull(env.getProperty("mq.basic.info.routing.key.name")));
            //发送消息
            rabbitTemplate.convertAndSend(loginDO, message -> {
                MessageProperties messageProperties = message.getMessageProperties();
                messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, LoginDO.class);
                return message;
            });
            log.info("成功发送消息队列{}", loginDO);
        } catch (Exception e) {
            log.error("消息发送失败{}", loginDO, e.fillInStackTrace());
        }
    }
}
