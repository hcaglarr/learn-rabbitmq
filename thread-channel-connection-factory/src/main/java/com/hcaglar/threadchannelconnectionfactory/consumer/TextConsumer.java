package com.hcaglar.threadchannelconnectionfactory.consumer;

import com.hcaglar.threadchannelconnectionfactory.utilty.Queries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Hüseyin ÇAĞLAR
 * @version 1.0
 * @since 3.03.2022
 */
@Component
public class TextConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextConsumer.class);

    @RabbitListener(queues = Queries.ROUTING_KEY)
    public void listener(String in){
        LOGGER.info(String.format("%s RabbitListener: %s", getClass(), in));
    }
}
