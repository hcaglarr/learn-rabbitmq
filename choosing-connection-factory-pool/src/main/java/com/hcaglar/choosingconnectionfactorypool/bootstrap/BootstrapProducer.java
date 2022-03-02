package com.hcaglar.choosingconnectionfactorypool.bootstrap;

import com.hcaglar.choosingconnectionfactorypool.producer.TextProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Hüseyin ÇAĞLAR
 * @version 1.0
 * @since 2.03.2022
 */
@Component
@Slf4j
public class BootstrapProducer {

    private final TextProducer m_userProducer;

    public BootstrapProducer(TextProducer userProducer) {
        m_userProducer = userProducer;
    }

    @PostConstruct
    public void send() {
        log.info(String.format("\n%s Send Method Triggered\n",getClass()));
        m_userProducer.send("HEllO RABBITMQ ♥️");
    }
}
