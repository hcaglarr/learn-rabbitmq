package com.hcaglar.threadchannelconnectionfactory.service;

import com.hcaglar.threadchannelconnectionfactory.producer.TextProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Hüseyin ÇAĞLAR
 * @version 1.0
 * @since 3.03.2022
 */
@Service
public class TextService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextService.class);
    private final TextProducer m_textProducer;

    public TextService(TextProducer textProducer) {
        m_textProducer = textProducer;
    }

    public void send(String in){
        LOGGER.info(String.format("%s send(%s)",getClass(), in));
        m_textProducer.send(in);
    }
}
