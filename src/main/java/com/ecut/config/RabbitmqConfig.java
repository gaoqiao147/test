package com.ecut.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhouwei
 */
@Configuration
@Slf4j
public class RabbitmqConfig {
    @Resource
    private CachingConnectionFactory connectionFactory;
    @Resource
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;
    @Resource
    private Environment env;

    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer() {
        //定义消息监听器所在的容器工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //设置容器工厂所用的实例
        factory.setConnectionFactory(connectionFactory);
        //设置消息在传输中的格式，在这里采用JSON的格式进行传输
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //设置并发消费者实例的初始数量。在这里为1个
        factory.setConcurrentConsumers(1);
        //设置并发消费者实例的最大数量。在这里为1个
        factory.setMaxConcurrentConsumers(1);
        //设置并发消费者实例中每个实例拉取的消息数量-在这里为1个
        factory.setPrefetchCount(1);
        return factory;
    }

    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer() {
        //定义消息监听器所在的容器工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //设置容器工厂所用的实例
        factoryConfigurer.configure(factory, connectionFactory);
        //设置消息在传输中的格式。在这里采用JSON的格式进行传输
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //设置消息的确认消费模式。在这里为NONE，表示不需要确认消费
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        //设置并发消费者实例的初始数量。在这里为10个
        factory.setConcurrentConsumers(10);
        //设置并发消费者实例的最大数量。在这里为15个
        factory.setMaxConcurrentConsumers(15);
        //设置并发消费者实例中每个实例拉取的消息数量。在这里为10个
        factory.setPrefetchCount(10);
        return factory;
    }

    /**
     * 自定义配置RabbitMQ发送消息的操作组件RabbitTemplate
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        //设置“发送消息后进行确认”
        connectionFactory.setPublisherConfirms(true);
        //设置“发送消息后返回确认信息”
        connectionFactory.setPublisherReturns(true);
        //构造发送消息组件实例对象
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        //发送消息后，如果发送成功，则输出“消息发送成功”的反馈信息
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> log.info("消息发送成功:correlationData({}),ack({}),cause({})",
                correlationData, ack, cause));
        //发送消息后，如果发送失败，则输出“消息发送失败-消息丢失”的反馈信息
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message));
        //最终返回RabbitMQ的操作组件实例RabbitTemplate
        return rabbitTemplate;
    }
    /**
     * 创建direct消息模型：队列、交换机、路由
     */
    /**
     * 1.1、创建队列
     */
    @Bean(name = "basicQueue")
    public Queue basicQueue() {
        return new Queue(Objects.requireNonNull(env.getProperty("mq.basic.info.queue.name")), true);
    }

    /**
     * 1.2、创建交换机
     */
    @Bean
    public DirectExchange basicExchange() {
        return new DirectExchange(env.getProperty("mq.basic.info.exchange.name"), true, false);
    }

    /**
     * 1.3、创建绑定关系
     */
    @Bean
    public Binding basicBinding() {
        return BindingBuilder.bind(basicQueue()).to(basicExchange()).with(env.getProperty("mq.basic.info.routing.key.name"));
    }

    /**
     * 创建死信队列消息模型
     */
    @Bean
    public Queue basicDeadQueue() {
        Map<String, Object> args = new HashMap<>();
        //死信交换机
        args.put("x-dead-letter-exchange", env.getProperty("mq.dead.exchange.name"));
        //死信路由
        args.put("x-dead-letter-routing-key", env.getProperty("mq.dead.routing.key.name"));
        //TTL死亡时间
        args.put("x-message-ttl", 10000);
        //返回死信队列
        return new Queue(env.getProperty("mq.dead.queue.name"), true, false, false, args);
    }

    /**
     * 基础的生产交换机
     */
    @Bean
    public TopicExchange basicProducerExchange() {
        return new TopicExchange(env.getProperty("mq.producer.exchange.name"), true, false);
    }

    /**
     * 基础的路由绑定
     */
    @Bean
    public Binding basicProducerBinding() {
        return BindingBuilder.bind(basicDeadQueue()).to(basicProducerExchange()).with(env.getProperty("mq.producer.routing.key.name"));
    }

    /**
     * 真正的消息队列，被消费者发现
     */
    @Bean
    public Queue basicRealQueue() {
        return new Queue(env.getProperty("mq.customer.queue.name"),true);
    }

    /**
     * 死信交换机
     */
    @Bean
    public TopicExchange basicDeadExchange() {
        return new TopicExchange(env.getProperty("mq.dead.exchange.name"),true,false);
    }

    /**
     * 死信路由
     */
    @Bean
    public Binding basicDeadBinding() {
        return BindingBuilder.bind(basicRealQueue()).to(basicDeadExchange()).with(env.getProperty("mq.dead.routing.key.name"));
    }
}