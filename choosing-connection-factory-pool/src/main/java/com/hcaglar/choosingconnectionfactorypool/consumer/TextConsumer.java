package com.hcaglar.choosingconnectionfactorypool.consumer;

import com.hcaglar.choosingconnectionfactorypool.utilty.ListenerQueries;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Hüseyin ÇAĞLAR
 * @version 1.0
 * @since 2.03.2022
 */
@Component
@Slf4j
public class TextConsumer {

    @RabbitListener(queues = ListenerQueries.ROUTING_KEY)
    public void listener(String out) {
        log.info(String.format("\n%s Listener Method Triggered : %s\n", getClass(),out));
    }
}
