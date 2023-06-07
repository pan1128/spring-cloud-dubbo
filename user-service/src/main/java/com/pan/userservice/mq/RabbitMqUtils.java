package com.pan.userservice.mq;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RabbitMqUtils {
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息到mq
     * @param exchange 交换机名称
     * @param routeKey 路由键
     * @param msg 消息
     */
    public void sendMsg(String exchange,String routeKey,Object msg) {

        rabbitTemplate.convertAndSend(exchange, routeKey,msg);

    }

    /**
     * 发送带有有效期的消息到mq
     * @param exchange 交换机名称
     * @param routeKey 路由键
     * @param msg 消息
     * @param ttl 过期时间 单位秒
     */
    public void sendTtlMsg(String exchange,String routeKey,Object msg,Long ttl) {
        rabbitTemplate.convertAndSend(exchange, routeKey, msg, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration(String.valueOf(ttl*1000));
                return message;
            }
        });

    }
}
