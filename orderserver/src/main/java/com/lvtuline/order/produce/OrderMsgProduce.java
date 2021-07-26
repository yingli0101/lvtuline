package com.lvtuline.order.produce;

import com.lvtuline.order.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

@Component
@Slf4j
public class OrderMsgProduce {

    @Resource
            @Qualifier("rabbitTemplateCustomize")
    RabbitTemplate rabbitTemplate;

    public void sendMsg(String content) {
        MessageProperties properties = new MessageProperties();
        String msgId = UUID.randomUUID().toString();
        properties.setMessageId(msgId);
        Message msg = new Message(content.getBytes(), properties);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_A, RabbitMQConfig.ROUTING_KEY_A, msg);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("rabbitmq接收到了消息：" + "msgId:" + msgId + ",ack:" + ack);
            if (!ack) {
                System.out.println("异常：生产者重新投递！");
                //todo 生产者重新投递
                sendMsg(content);
            }
        });
        rabbitTemplate.setReturnCallback((Message message, int replyCode, String replyText, String exchange, String routingKey) -> {
            log.info("return exchange: " + exchange + ", routingKey: " + routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);
        });
    }
}
