package com.lvtuline.stock.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;


@Component
@Slf4j
public class OrderMsgConsumer implements ChannelAwareMessageListener {

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @RabbitListener(queues = "queue_a")
    public void onMessage(Message message, Channel channel) throws IOException {
        log.info("消费者接收到消息：" + message);
        MessageProperties messageProperties = message.getMessageProperties();
        String messageId = messageProperties.getMessageId();
        System.out.println(messageId);
        long deliveryTag = messageProperties.getDeliveryTag();
        try {
            //todo 消费者接收到消息 以msgId为key，插入redis，下次先查询是否有此key 或 对于成功消费的数据 进行插入数据库操作，以消息id为主键
            if (redisTemplate.opsForValue().setIfAbsent(messageId, "1")) {
                //消息已经被消费
                log.info("幂等处理，messageId:{},插入redis成功", messageId);
                channel.basicAck(deliveryTag, false);
            }
            log.error("消息已经被消费,无法消费！");
            //拒绝处理
            //int i = 1 / 0;

        } catch (Exception e) {
            log.info("handleEmailMessage捕获到异常,拒绝重新入队，消息id：" + messageProperties.getDeliveryTag());
            channel.basicNack(deliveryTag, false, false);
        }

    }

    //死信队列消费
    @RabbitListener(queues = "dlxQueue")
    public void onDleMessage(Message message, Channel channel) throws IOException {
        log.info("死信队列接收到消息：" + message);
        MessageProperties messageProperties = message.getMessageProperties();
        String messageId = messageProperties.getMessageId();
        System.out.println(messageId);
        long deliveryTag = messageProperties.getDeliveryTag();
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            e.printStackTrace();
            channel.basicNack(deliveryTag, false, false);
        }

    }


}
