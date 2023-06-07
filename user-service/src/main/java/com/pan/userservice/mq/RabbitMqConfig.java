package com.pan.userservice.mq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public Exchange directExchange() {
        return ExchangeBuilder.directExchange("ybp.exchange")
                .durable(true)
                .build();
    }

    /**
     * 队列绑定 死信交换机已经routingKey
     * @return
     */
    @Bean
    public Queue queue() {

        return QueueBuilder.durable("ybp.queue")
                .deadLetterExchange("ssl.exchange")
                .deadLetterRoutingKey("ssl")
                .build();
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue())
                .to(directExchange())
                .with("ybp").noargs();
    }
}
