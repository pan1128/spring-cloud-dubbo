package com.pan.userservice.mq;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MqMsgHandler {

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name="ssl.queue",durable = "true"),
                    exchange = @Exchange(name = "ssl.exchange",type = "direct",durable = "true"),
                    key = "ssl"
            )
    )
    public void msgHandler(String msg){
        System.out.println(msg);
    }
}
