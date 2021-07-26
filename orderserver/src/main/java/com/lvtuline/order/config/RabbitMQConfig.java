package com.lvtuline.order.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    public final static String QUEUE_A = "queue_a";
    public final static String QUEUE_B = "queue_b";
    public final static String QUEUE_C = "queue_c";


    public final static String ROUTING_KEY_A = "routing_key_A";

    public final static String EXCHANGE_A = "exchange_a";
    public final static String EXCHANGE_B = "exchange_b";
    public final static String EXCHANGE_C = "exchange_c";

    /**
     * 死信交换机
     *
     * @return
     */
    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange("dlxExchange");
    }

    /**
     * 死信队列
     *
     * @return
     */
    @Bean
    public Queue dlxQueue() {

        return new Queue("dlxQueue");
    }

    /**
     * 死信队列绑定死信交换机
     *
     * @param dlxQueue
     * @param dlxExchange
     * @return
     */
    @Bean
    public Binding dlxBinding(Queue dlxQueue, DirectExchange dlxExchange) {
        return BindingBuilder.bind(dlxQueue).to(dlxExchange).with("dlxRoutingKey");
    }

    //业务队列
    @Bean
    public Queue queueA() {
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", "dlxExchange");//声明当前队列绑定的死信交换机
        params.put("x-dead-letter-routing-key", "dlxRoutingKey");//声明当前队列的死信路由键
        return QueueBuilder.durable(QUEUE_A).withArguments(params).build();
    }

    /*@Bean
    public Queue queueB() {
        return new Queue(QUEUE_B, true);
    }

    @Bean
    public Queue queueC() {
        return new Queue(QUEUE_C, true);
    }*/
    @Bean
    public DirectExchange exchangeA() {
        return new DirectExchange(EXCHANGE_A, true, false);
    }

    @Bean
    public Binding queueABindingExchangeA() {
        return BindingBuilder.bind(queueA()).to(exchangeA()).with(ROUTING_KEY_A);
    }

    @Bean(name = "rabbitTemplateCustomize")
    @Scope("prototype")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMandatory(true);
        template.setMessageConverter(new SerializerMessageConverter());
        return template;
    }

}
