package com.hcaglar.threadchannelconnectionfactory.producer;

import com.hcaglar.threadchannelconnectionfactory.utilty.Queries;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author Hüseyin ÇAĞLAR
 * @version 1.0
 * @since 3.03.2022
 */
@Component
public class TextProducer {

    private final RabbitTemplate rabbitTemplate;

    public TextProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String out){
        rabbitTemplate.send(Queries.ROUTING_KEY, new Message(out.getBytes(StandardCharsets.UTF_8)));
    }
}
