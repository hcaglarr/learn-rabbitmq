package com.hcaglar.choosingconnectionfactorypool.producer;

import com.hcaglar.choosingconnectionfactorypool.utilty.ListenerQueries;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author Hüseyin ÇAĞLAR
 * @version 1.0
 * @since 2.03.2022
 */
@Component
@Slf4j
public class TextProducer {
    private final RabbitTemplate rabbitTemplate;

    public TextProducer(RabbitTemplate rabbitTemplate)  {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String in)  {
        log.info(String.format("%s Send Method",getClass()));
        rabbitTemplate.send(ListenerQueries.ROUTING_KEY, new Message(in.getBytes(StandardCharsets.UTF_8)));
    }

}
